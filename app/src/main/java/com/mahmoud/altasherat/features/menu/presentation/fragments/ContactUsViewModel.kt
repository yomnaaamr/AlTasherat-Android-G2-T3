package com.mahmoud.altasherat.features.menu.presentation.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.usecase.GetUserInfoUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val getUserInfoUC: GetUserInfoUC,
) : ViewModel() {


    private val _state = MutableStateFlow(ContactUsContract.ContactUsState())
    val state = _state.asStateFlow()

    private val _events = Channel<ContactUsContract.ContactUsEvent>()
    val events = _events.receiveAsFlow()


    init {
        getUserData()
    }


    private fun getUserData() {
        viewModelScope.launch {
            getUserInfoUC()
                .collect { result ->
                    _state.update {
                        when (result) {
                            is Resource.Loading -> it.copy(screenState = ContactUsContract.ContactUsScreenState.Loading)
                            is Resource.Success -> it.copy(
                                user = result.data,
                                screenState = ContactUsContract.ContactUsScreenState.Success
                            )

                            is Resource.Error -> {
                                _events.send(ContactUsContract.ContactUsEvent.Error(result.error))
                                it.copy(
                                    screenState = ContactUsContract.ContactUsScreenState.Error(
                                        result.error
                                    )
                                )
                            }
                        }
                    }
                }

        }
    }

}