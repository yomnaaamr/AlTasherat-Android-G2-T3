package com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.remote

import com.mahmoud.altasherat.features.al_tashirat_services.country.data.models.dto.CountriesDto

internal interface ICountryRemoteDS {
    suspend fun getCountries(languageCode: String): CountriesDto
}