package com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.local

import com.mahmoud.altasherat.features.al_tashirat_services.country.data.models.entity.CountriesEntity
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country

internal interface ICountryLocalDS {
    suspend fun savaCountries(countriesEntity: CountriesEntity)
    suspend fun getCountries(): CountriesEntity
    suspend fun hasCountries(): Boolean
    suspend fun saveSelectedCountry(selectedCountry: Country)
    suspend fun getCountry(): Country

}