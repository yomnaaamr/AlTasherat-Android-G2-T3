package com.mahmoud.altasherat.features.menu_options.change_password.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.features.menu_options.change_password.data.models.request.ChangePassRequest
import com.mahmoud.altasherat.features.menu_options.change_password.domain.models.ChangePassword

class ChangePasswordContract {
    data class ChangePasswordUIState(
        val isLoading: Boolean = false,
        val response: ChangePasswordAction.ChangePassword? = null,
        val screenState: ChangePasswordState = ChangePasswordState.Idle
    )

    sealed interface ChangePasswordAction {
        data class ChangePassword(val passwordRequest: ChangePassRequest) : ChangePasswordAction
    }

    sealed interface ChangePasswordEvent {
        data class Error(val error: AltasheratError) : ChangePasswordEvent
        data object NavigationToMenu : ChangePasswordEvent
    }

    sealed class ChangePasswordState {
        data object Idle : ChangePasswordState()
        data object Loading : ChangePasswordState()
        data class Success(val response: ChangePassword) : ChangePasswordState()
        data class Error(val error: AltasheratError) : ChangePasswordState()
    }
}