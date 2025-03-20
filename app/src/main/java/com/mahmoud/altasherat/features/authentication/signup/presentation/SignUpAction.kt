package com.mahmoud.altasherat.features.authentication.signup.presentation

sealed interface SignUpAction {

    data object SignUp:
        com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction
    data class UpdateFirstName(val value: String) :
        com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction
    data class UpdateLastName(val value: String) :
        com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction
    data class UpdateEmail(val value: String) :
        com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction
    data class UpdatePassword(val value: String) :
        com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction
    data class UpdatePhoneNumber(val phone: String) :
        com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction
    data class UpdateCountryID(val countryId: String) :
        com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction
    data class UpdateCountryCode(val countryCode: String) :
        com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction
}