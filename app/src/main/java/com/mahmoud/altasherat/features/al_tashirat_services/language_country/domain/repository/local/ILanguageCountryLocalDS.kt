package com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.local

import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.models.entity.CountriesEntity
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language

internal interface ILanguageCountryLocalDS {
    suspend fun savaCountries(countriesEntity: CountriesEntity)
    suspend fun getCountries(): CountriesEntity
    suspend fun saveSelections(selectedLanguage: Language, selectedCountry: Country)
    suspend fun getLanguageCode(): String?
    suspend fun hasCountries(): Boolean
    suspend fun getCountry(): Country

}