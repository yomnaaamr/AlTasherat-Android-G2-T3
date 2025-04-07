package com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.repository.local

import com.google.gson.Gson
import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.models.entity.CountriesEntity
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.local.ILanguageCountryLocalDS

internal class LanguageCountryLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
    private val gson: Gson
) : ILanguageCountryLocalDS {

    override suspend fun savaCountries(countriesEntity: CountriesEntity) {
        val countryJson = gson.toJson(countriesEntity)
        localStorageProvider.save(StorageKeyEnum.COUNTRIES, countryJson, String::class)
    }

    override suspend fun getCountries(): CountriesEntity {
        val countriesJson = localStorageProvider.get(StorageKeyEnum.COUNTRIES, "", String::class)
        return gson.fromJson(countriesJson, CountriesEntity::class.java)
    }

    override suspend fun getLanguageCode(): String? {
        val selectedLanguageJson =
            localStorageProvider.get(StorageKeyEnum.SELECTED_LANGUAGE, "", String::class)
        if (selectedLanguageJson.isNotEmpty()) {
            val selectedLanguage = gson.fromJson(selectedLanguageJson, Language::class.java)
            return selectedLanguage.code
        }
        return null
    }

    override suspend fun hasCountries(): Boolean {
        return localStorageProvider.contains(StorageKeyEnum.COUNTRIES, String::class)
    }

    override suspend fun saveSelectedLanguage(selectedLanguage: Language) {
        val selectedLanguageJson = gson.toJson(selectedLanguage)
        localStorageProvider.save(
            StorageKeyEnum.SELECTED_LANGUAGE,
            selectedLanguageJson,
            String::class
        )
    }

    override suspend fun saveSelectedCountry(selectedCountry: Country) {
        val selectedCountryJson = gson.toJson(selectedCountry)
        localStorageProvider.save(
            StorageKeyEnum.SELECTED_COUNTRY,
            selectedCountryJson,
            String::class
        )
    }

    override suspend fun getCountry(): Country {
        val selectedCountryJson =
            localStorageProvider.get(StorageKeyEnum.SELECTED_COUNTRY, "", String::class)
        return gson.fromJson(selectedCountryJson, Country::class.java)
    }

    override suspend fun deleteSelectedCountry() {
        localStorageProvider.delete(StorageKeyEnum.SELECTED_COUNTRY, Unit::class)
    }
}