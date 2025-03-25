package com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository

import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.models.dto.CountriesDto
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Countries

interface ILanguageCountryRepository {
    suspend fun getCountriesFromRemote(languageCode: String): Countries
    suspend fun savaCountriesToLocal(countriesResponse: Countries)
    suspend fun getCountriesFromLocal(): List<Country>
    suspend fun getLanguageCode(): String?
    suspend fun hasCountries(): Boolean
    suspend fun saveSelectedLanguage(selectedLanguage: Language)
    suspend fun saveSelectedCountry(selectedCountry: Country)

}