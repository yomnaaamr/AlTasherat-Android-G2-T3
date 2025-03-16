package com.mahmoud.altasherat.features.language_country.domain.repository

import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.common.domain.models.ListItem

interface ILanguageCountryRepository {
    suspend fun getCountries(): List<Country>
    suspend fun saveSelections(selectedLanguage: ListItem, selectedCountry: ListItem)
    suspend fun getLanguageCode(): String?

}