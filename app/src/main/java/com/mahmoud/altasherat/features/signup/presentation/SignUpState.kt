package com.mahmoud.altasherat.features.signup.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.features.signup.domain.models.SignUp

sealed class SignUpState {
    data object Idle : SignUpState()
    data object Loading : SignUpState()
    data class Success (val signupResponse: SignUp) : SignUpState()
    data class Error(val error: AltasheratError) : SignUpState()
}