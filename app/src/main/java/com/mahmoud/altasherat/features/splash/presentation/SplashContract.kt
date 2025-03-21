package com.mahmoud.altasherat.features.splash.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError

class SplashContract {

    sealed interface SplashEvent {
        data class Error(val error: AltasheratError) : SplashEvent
        data object NavigateToHome : SplashEvent
        data object NavigateToOnBoarding : SplashEvent
        data object NavigateToAuth : SplashEvent
    }

    sealed interface SplashState {
        data object Idle : SplashState
        data object Loading : SplashState
        data object Success : SplashState
        data class Error(val error: AltasheratError) : SplashState
    }


    sealed interface SplashAction {

    }
}