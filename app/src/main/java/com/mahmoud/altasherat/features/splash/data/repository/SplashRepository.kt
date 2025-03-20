package com.mahmoud.altasherat.features.splash.data.repository

import com.mahmoud.altasherat.features.splash.domain.repository.ISplashRepository
import com.mahmoud.altasherat.features.splash.domain.repository.local.ISplashLocalDS

internal class SplashRepository(
    private val localDS: ISplashLocalDS
): ISplashRepository {
    override suspend fun getUserAccessToken(): String {
        return localDS.getUserAccessToken()
    }

}