package com.mahmoud.altasherat.features.menu_options.change_password.presentation

import androidx.lifecycle.ViewModel
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.features.menu_options.change_password.data.models.request.ChangePassRequest
import com.mahmoud.altasherat.features.menu_options.change_password.domain.usecase.ChangePasswordUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePassUC: ChangePasswordUC
) : ViewModel() {

    private val _state =
        MutableStateFlow<ChangePasswordContract.ChangePasswordUIState>(ChangePasswordContract.ChangePasswordUIState())
    val state = _state.asStateFlow()

    private val _events = Channel<ChangePasswordContract.ChangePasswordEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(changePassAction: ChangePasswordContract.ChangePasswordAction) {
        when (changePassAction) {
            is ChangePasswordContract.ChangePasswordAction.ChangePassword -> changePassword(
                changePassAction.passwordRequest
            )
        }
    }

    private fun changePassword(request: ChangePassRequest) {
        changePassUC.invoke(request).onEach { result ->
            when (result) {
                is Resource.Loading -> _state.update {
                    it.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _events.send(ChangePasswordContract.ChangePasswordEvent.NavigationToMenu)
                    _state.update {
                        it.copy(
                            response = result.data
                        )
                    }
                }

                is Resource.Error -> {
                    _events.send(ChangePasswordContract.ChangePasswordEvent.Error(result.error))
                }
            }
        }

    }
}