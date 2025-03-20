package com.mahmoud.altasherat.features.signup.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class SignUpResponseDto(
    @SerializedName("message") val message: String? = null,
    @SerializedName("token") val token: String? = null,
    @SerializedName("user") val user: UserDto? = null
)