package com.mahmoud.altasherat.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.features.login.data.models.request.LoginRequest
import com.mahmoud.altasherat.features.login.domain.useCases.PhoneLoginUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val phoneLoginUC: PhoneLoginUC) : ViewModel() {

    private val _state = MutableStateFlow(LoginContract.LoginState.initial())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<LoginContract.LoginEvent>()
    val event = _event.asSharedFlow()

    fun onActionTrigger(action: LoginContract.LoginAction) {
        when (action) {
            is LoginContract.LoginAction.LoginWithPhone -> loginWithPhone(action.loginRequest)
        }
    }

    private fun loginWithPhone(loginRequest: LoginRequest) {
        phoneLoginUC(loginRequest).onEach { resource ->
            when (resource) {
                is Resource.Loading -> _state.update { oldViewState -> oldViewState.copy(isLoading = true) }
                is Resource.Error -> _state.update { oldViewState ->
                    oldViewState.copy(
                        altasheratError = resource.error
                    )
                }

                is Resource.Success -> _event.emit(LoginContract.LoginEvent.NavigateToHome(resource.data.user))
            }
        }.launchIn(viewModelScope)
    }
}
