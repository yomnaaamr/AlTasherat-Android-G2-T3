package com.mahmoud.altasherat.features.menu_options.contact_us.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.onEachErrorSuspend
import com.mahmoud.altasherat.common.domain.util.onEachSuccessSuspend
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.usecase.GetUserInfoUC
import com.mahmoud.altasherat.features.splash.domain.usecase.HasUserLoggedInUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val getUserInfoUC: GetUserInfoUC,
    private val hasUserLoggedInUC: HasUserLoggedInUC,
    ) : ViewModel() {


    private val _state = MutableStateFlow(ContactUsContract.ContactUsState())
    val state = _state.asStateFlow()

    private val _events = Channel<ContactUsContract.ContactUsEvent>()
    val events = _events.receiveAsFlow()


    init {
        getUserData()
    }


    private fun getUserData() {

         hasUserLoggedInUC()
            .onEachSuccessSuspend { hasUser ->
                if (hasUser) {
                    getUserInfo()
                }else return@onEachSuccessSuspend
            }
             .onEachErrorSuspend { error ->
                 _events.send(ContactUsContract.ContactUsEvent.Error(error))
                 _state.update {
                     it.copy(
                         screenState = ContactUsContract.ContactUsScreenState.Error(error)
                     )
                 }
             }
             .launchIn(viewModelScope)


    }


    private fun getUserInfo(){
        getUserInfoUC()
            .onEachSuccessSuspend { result ->
                _state.update {
                    it.copy(
                        user = result,
                        screenState = ContactUsContract.ContactUsScreenState.Success
                    )
                }
            }
            .onEachErrorSuspend { error ->
                _events.send(ContactUsContract.ContactUsEvent.Error(error))
                _state.update {
                    it.copy(
                        screenState = ContactUsContract.ContactUsScreenState.Error(error)
                    )
                }
            }.launchIn(viewModelScope)
    }

}