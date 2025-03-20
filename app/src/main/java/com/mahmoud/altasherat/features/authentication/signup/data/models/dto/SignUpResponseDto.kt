package com.mahmoud.altasherat.features.authentication.signup.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class SignUpResponseDto(
    @SerializedName("message") val message: String? = null,
    @SerializedName("token") val token: String? = null,
    @SerializedName("user") val user: com.mahmoud.altasherat.features.authentication.signup.data.models.dto.UserDto? = null
)