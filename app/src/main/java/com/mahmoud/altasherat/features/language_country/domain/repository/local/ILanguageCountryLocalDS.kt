package com.mahmoud.altasherat.features.language_country.domain.repository.local

import com.mahmoud.altasherat.common.domain.models.ListItem
import com.mahmoud.altasherat.features.splash.data.models.entity.SplashEntity

internal interface ILanguageCountryLocalDS {
    suspend fun getCountries(): SplashEntity
    suspend fun saveSelections(selectedLanguage: ListItem, selectedCountry: ListItem)
}