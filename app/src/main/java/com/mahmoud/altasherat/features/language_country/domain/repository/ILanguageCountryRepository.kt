package com.mahmoud.altasherat.features.language_country.domain.repository

import com.mahmoud.altasherat.features.splash.domain.models.Country

interface ILanguageCountryRepository {
    suspend fun getCountries(): List<Country>
}