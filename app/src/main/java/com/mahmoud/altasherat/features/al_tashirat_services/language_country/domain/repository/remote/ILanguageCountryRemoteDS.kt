package com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.remote

import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.models.dto.CountriesDto

internal interface ILanguageCountryRemoteDS {
    suspend fun getCountries(): CountriesDto
}