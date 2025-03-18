package com.mahmoud.altasherat.features.signup.presentation

sealed interface SignUpAction {

    data object SignUp: SignUpAction
}