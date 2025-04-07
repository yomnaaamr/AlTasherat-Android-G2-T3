package com.mahmoud.altasherat.features.al_tashirat_services.country.data.models.dto

import com.google.gson.annotations.SerializedName

data class CountriesDto(
    @SerializedName("data") val data: List<CountryDto>? = null,
)