package com.mahmoud.altasherat.features.splash.data.repository.local

import com.google.gson.Gson
import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.models.entity.CountriesEntity
import com.mahmoud.altasherat.features.splash.domain.repository.local.ISplashLocalDS

internal class SplashLocalDS(
    private val localStorageProvider: ILocalStorageProvider
) : ISplashLocalDS {
    override suspend fun getUserAccessToken(): String {
        return localStorageProvider.get(StorageKeyEnum.ACCESS_TOKEN, "", String::class)
    }

}