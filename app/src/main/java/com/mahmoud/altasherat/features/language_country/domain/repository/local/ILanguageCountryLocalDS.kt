package com.mahmoud.altasherat.features.language_country.domain.repository.local

import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.common.domain.models.Language
import com.mahmoud.altasherat.features.splash.data.models.entity.SplashEntity

internal interface ILanguageCountryLocalDS {
    suspend fun getCountries(): SplashEntity
    suspend fun saveSelections(selectedLanguage: Language, selectedCountry: Country)
    suspend fun getLanguageCode(): String?

}