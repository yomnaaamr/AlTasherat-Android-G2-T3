package com.mahmoud.altasherat.features.authentication.login.data.models.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PhoneRequest(
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("number") val number: String
)
