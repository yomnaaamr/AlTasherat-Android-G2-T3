package com.mahmoud.altasherat.features.login.data.models.dto

import kotlinx.serialization.SerialName

data class PhoneDto(
    @SerialName("id")
    val id: Int,
    @SerialName("country_code")
    val countryCode: String? = null,
    @SerialName("extension")
    val extension: Any? = null,
    @SerialName("holder_name")
    val holderName: String? = null,
    @SerialName("number")
    val number: String,
    @SerialName("type")
    val type: String
)