package com.mahmoud.altasherat.features.authentication.signup.presentation

data class SignUpUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val countryCode: String = "",
    val phoneNumber: String = "",
    val selectedCountryId: String = "",
)