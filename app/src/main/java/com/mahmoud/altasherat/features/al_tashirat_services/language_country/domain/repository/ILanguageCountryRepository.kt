package com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository

import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Countries
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language

interface ILanguageCountryRepository {
    suspend fun getCountriesFromRemote(): Countries
    suspend fun savaCountriesToLocal(countriesResponse: Countries)
    suspend fun getCountriesFromLocal(): List<Country>
    suspend fun saveSelections(selectedLanguage: Language, selectedCountry: Country)
    suspend fun getLanguageCode(): String?
    suspend fun hasCountries(): Boolean
    suspend fun getCountry(): Country


}