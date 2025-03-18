package com.mahmoud.altasherat.features.login.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.login.data.models.request.LoginRequest
import com.mahmoud.altasherat.features.login.domain.models.User

class LoginContract {
    sealed interface LoginAction {
        data class LoginWithPhone(val loginRequest: LoginRequest) : LoginAction
    }

    sealed class LoginEvent {
        data class NavigateToHome(val user: User) : LoginEvent()
    }

    data class LoginState(
        val isLoading: Boolean, val altasheratError: AltasheratError?, val action: LoginAction?,
    ) {
        companion object {
            fun initial() = LoginState(
                isLoading = false, altasheratError = null, action = null
            )
        }
    }
}