package com.mahmoud.altasherat.features.profile_info.presentation

import java.io.File

data class ProfileInfoUiState(
    val image: File? = null,
    val firstName: String = "",
    val middleName: String? = "",
    val lastName: String = "",
    val countryCode: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val birthdate: String? = "",
    val selectedCountryId: String = "",
)