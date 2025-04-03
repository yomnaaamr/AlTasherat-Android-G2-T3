package com.mahmoud.altasherat.features.menu.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError

class MenuContract {


    sealed interface MenuEvent {
        data class Error(val error: AltasheratError) : MenuEvent
    }

    sealed interface MenuState {
        data object Idle : MenuState
        data object Loading : MenuState
        data class Success(val isAuthenticated: Boolean = false) : MenuState
        data class Error(val error: AltasheratError) : MenuState
    }


    sealed interface MenuAction {

    }
}