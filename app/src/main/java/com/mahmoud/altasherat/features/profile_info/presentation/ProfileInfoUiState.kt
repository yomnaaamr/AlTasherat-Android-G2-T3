package com.mahmoud.altasherat.features.profile_info.presentation

import android.net.Uri

data class ProfileInfoUiState(
    val image: Uri? = null,
    val firstName: String = "",
    val middleName: String? = "",
    val lastName: String = "",
    val countryCode: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val birthdate: String? = "",
    val selectedCountryId: String = "",
)