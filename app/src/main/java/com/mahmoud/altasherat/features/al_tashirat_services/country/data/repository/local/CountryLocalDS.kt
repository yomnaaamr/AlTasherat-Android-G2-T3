package com.mahmoud.altasherat.features.al_tashirat_services.country.data.repository.local

import com.google.gson.Gson
import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.al_tashirat_services.country.data.models.entity.CountriesEntity
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.local.ICountryLocalDS

internal class CountryLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
    private val gson: Gson
) : ICountryLocalDS {

    override suspend fun savaCountries(countriesEntity: CountriesEntity) {
        val countryJson = gson.toJson(countriesEntity)
        localStorageProvider.save(StorageKeyEnum.COUNTRIES, countryJson, String::class)
    }

    override suspend fun getCountries(): CountriesEntity {
        val countriesJson = localStorageProvider.get(StorageKeyEnum.COUNTRIES, "", String::class)
        return gson.fromJson(countriesJson, CountriesEntity::class.java)
    }


    override suspend fun hasCountries(): Boolean {
        return localStorageProvider.contains(StorageKeyEnum.COUNTRIES, String::class)
    }



    override suspend fun saveSelectedCountry(selectedCountry: Country) {
        val selectedCountryJson = gson.toJson(selectedCountry)
        localStorageProvider.save(StorageKeyEnum.SELECTED_COUNTRY, selectedCountryJson, String::class)
    }

    override suspend fun getCountry(): Country {
        val selectedCountryJson =
            localStorageProvider.get(StorageKeyEnum.SELECTED_COUNTRY, "", String::class)
        return gson.fromJson(selectedCountryJson, Country::class.java)
    }
}