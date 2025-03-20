package com.mahmoud.altasherat.features.authentication.signup.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError


sealed interface SignUpEvent {
    data class Error(val error: AltasheratError) : SignUpEvent
    data object NavigationToHome: SignUpEvent
}