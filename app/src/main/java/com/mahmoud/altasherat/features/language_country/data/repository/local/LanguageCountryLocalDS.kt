package com.mahmoud.altasherat.features.language_country.data.repository.local

import com.google.gson.Gson
import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.common.domain.models.Language
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.language_country.domain.repository.local.ILanguageCountryLocalDS
import com.mahmoud.altasherat.features.splash.data.models.entity.SplashEntity

internal class LanguageCountryLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
    private val gson: Gson
): ILanguageCountryLocalDS {

    override suspend fun getCountries(): SplashEntity {
        val countriesJson = localStorageProvider.get(StorageKeyEnum.COUNTRIES, "", String::class)
        return gson.fromJson(countriesJson, SplashEntity::class.java)
    }

    override suspend fun saveSelections(selectedLanguage: Language, selectedCountry: Country) {
        val selectedLanguageJson = gson.toJson(selectedLanguage)
        val selectedCountryJson = gson.toJson(selectedCountry)
        localStorageProvider.save(StorageKeyEnum.SELECTED_LANGUAGE, selectedLanguageJson, String::class)
        localStorageProvider.save(StorageKeyEnum.SELECTED_COUNTRY, selectedCountryJson, String::class)
    }

    override suspend fun getLanguageCode(): String? {
        val selectedLanguageJson = localStorageProvider.get(StorageKeyEnum.SELECTED_LANGUAGE, "", String::class)
        if (selectedLanguageJson.isNotEmpty()) {
            val selectedLanguage = gson.fromJson(selectedLanguageJson, Language::class.java)
            return selectedLanguage.code
        }
        return null
    }
}