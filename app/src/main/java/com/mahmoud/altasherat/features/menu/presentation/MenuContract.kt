package com.mahmoud.altasherat.features.menu.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User

class MenuContract {


    data class MenuState(
        val isAuthenticated: Boolean = false,
        val user: User? = null,
        val screenState: MenuScreenState = MenuScreenState.Idle
    )

    sealed interface MenuEvent {
        data class Error(val error: AltasheratError) : MenuEvent
    }

    sealed interface MenuScreenState {
        data object Idle : MenuScreenState
        data object Loading : MenuScreenState
        data object Success : MenuScreenState
        data class Error(val error: AltasheratError) : MenuScreenState
    }


    sealed interface MenuAction {
        data object GetUserData : MenuAction
    }
}