package com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.models.dto

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("currency") val currency: String? = null,
    @SerializedName("code") val code: String? = null,
    @SerializedName("phone_code") val phoneCode: String? = null,
    @SerializedName("flag") val flag: String? = null
)