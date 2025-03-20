package com.mahmoud.altasherat.features.splash.data.repository.local

import com.google.gson.Gson
import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.splash.data.models.entity.SplashEntity
import com.mahmoud.altasherat.features.splash.domain.repository.local.ISplashLocalDS

internal class SplashLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
    private val gson: Gson
) : ISplashLocalDS {

    override suspend fun savaCountry(splashEntity: SplashEntity) {
        val countryJson = gson.toJson(splashEntity)
        localStorageProvider.save(StorageKeyEnum.COUNTRIES, countryJson, String::class)
    }

    override suspend fun getUserAccessToken(): String {
        return localStorageProvider.get(StorageKeyEnum.ACCESS_TOKEN, "", String::class)
    }

}