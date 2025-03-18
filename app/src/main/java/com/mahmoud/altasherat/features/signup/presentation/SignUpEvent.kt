package com.mahmoud.altasherat.features.signup.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError


sealed interface SignUpEvent {
    data class Error(val error: AltasheratError) : SignUpEvent
}