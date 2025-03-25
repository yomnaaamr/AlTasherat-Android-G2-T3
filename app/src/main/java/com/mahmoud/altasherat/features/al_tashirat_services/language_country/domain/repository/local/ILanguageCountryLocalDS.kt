package com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.local

import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.models.entity.CountriesEntity
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language

internal interface ILanguageCountryLocalDS {
    suspend fun savaCountries(countriesEntity: CountriesEntity)
    suspend fun getCountries(): CountriesEntity
    suspend fun getLanguageCode(): String?
    suspend fun hasCountries(): Boolean
    suspend fun saveSelectedLanguage(selectedLanguage: Language)
    suspend fun saveSelectedCountry(selectedCountry: Country)
    suspend fun getCountry(): Country

}