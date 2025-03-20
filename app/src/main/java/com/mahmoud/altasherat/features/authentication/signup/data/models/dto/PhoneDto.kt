package com.mahmoud.altasherat.features.authentication.signup.data.models.dto

import com.google.gson.annotations.SerializedName

internal data class PhoneDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("country_code") val countryCode: String? = null,
    @SerializedName("number") val number: String? = null,
    @SerializedName("extension") val extension: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("holder_name") val holderName: String? = null
)