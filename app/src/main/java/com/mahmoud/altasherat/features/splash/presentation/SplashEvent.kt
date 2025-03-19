package com.mahmoud.altasherat.features.splash.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError

sealed interface SplashEvent {
    data class Error(val error: AltasheratError) : SplashEvent
    data object NavigateToHome : SplashEvent
    data object NavigateToOnBoarding : SplashEvent
    data object NavigateToAuth : SplashEvent
}