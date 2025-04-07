package com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository

import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Countries
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country

interface ICountryRepository {
    suspend fun getCountriesFromRemote(languageCode: String): Countries
    suspend fun savaCountriesToLocal(countriesResponse: Countries)
    suspend fun getCountriesFromLocal(): List<Country>
    suspend fun hasCountries(): Boolean
    suspend fun saveSelectedCountry(selectedCountry: Country)
    suspend fun getCountry(): Country


}