package com.mahmoud.altasherat.features.splash.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError

sealed class SplashState {
//    data object Idle : SplashState()
    data object Loading : SplashState()
    data object Success : SplashState()
    data class Error(val error: AltasheratError) : SplashState()
}