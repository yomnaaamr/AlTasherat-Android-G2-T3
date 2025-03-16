package com.mahmoud.altasherat.features.language_country.data.repository.local

import com.google.gson.Gson
import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
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
}