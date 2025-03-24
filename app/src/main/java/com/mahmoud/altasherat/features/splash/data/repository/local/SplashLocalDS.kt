package com.mahmoud.altasherat.features.splash.data.repository.local

import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.splash.domain.repository.local.ISplashLocalDS

internal class SplashLocalDS(
    private val localStorageProvider: ILocalStorageProvider
) : ISplashLocalDS {
    override suspend fun getUserAccessToken(): String {
        return localStorageProvider.get(StorageKeyEnum.ACCESS_TOKEN, "", String::class)
    }

    override suspend fun hasUserLoggedIn(): Boolean {
        return localStorageProvider.contains(StorageKeyEnum.ACCESS_TOKEN,String::class)
    }

}