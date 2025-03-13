package com.mahmoud.altasherat.features.splash.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class SplashResponseDto(
    @SerializedName("data") val data: List<CountryDto>? = null,
)