package com.mahmoud.altasherat.features.signup.presentation

sealed interface SignUpAction {

    data object SignUp: SignUpAction
    data class UpdateFirstName(val value: String) : SignUpAction
    data class UpdateLastName(val value: String) : SignUpAction
    data class UpdateEmail(val value: String) : SignUpAction
    data class UpdatePassword(val value: String) : SignUpAction
    data class UpdatePhoneNumber(val phone: String) : SignUpAction
    data class UpdateCountryID(val countryId: String) : SignUpAction
    data class UpdateCountryCode(val countryCode: String) : SignUpAction
}