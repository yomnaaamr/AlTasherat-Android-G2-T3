package com.mahmoud.altasherat.features.language_country.domain.repository

import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.common.domain.models.Language

interface ILanguageCountryRepository {
    suspend fun getCountries(): List<Country>
    suspend fun saveSelections(selectedLanguage: Language, selectedCountry: Country)
    suspend fun getLanguageCode(): String?

}