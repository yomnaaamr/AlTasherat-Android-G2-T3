package com.mahmoud.altasherat.features.authentication.login.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User
import com.mahmoud.altasherat.features.authentication.login.data.models.request.LoginRequest

class LoginContract {
    sealed interface LoginAction {
        data class LoginWithPhone(val loginRequest: LoginRequest) : LoginAction
    }

    sealed class LoginEvent {
        data class NavigateToHome(val user: User) : LoginEvent()
        data class Error(val error: AltasheratError) : LoginEvent()
    }

    sealed interface LoginState {
        data object Idle : LoginState
        data object Loading : LoginState
        data object Success : LoginState
        data class Exception(val altasheratError: AltasheratError) : LoginState

    }
}