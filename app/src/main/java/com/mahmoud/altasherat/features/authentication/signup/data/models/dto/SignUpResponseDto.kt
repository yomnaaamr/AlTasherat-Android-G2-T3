package com.mahmoud.altasherat.features.authentication.signup.data.models.dto

import com.google.gson.annotations.SerializedName
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.UserDto

internal data class SignUpResponseDto(
    @SerializedName("message") val message: String? = null,
    @SerializedName("token") val token: String? = null,
    @SerializedName("user") val user: UserDto? = null
)