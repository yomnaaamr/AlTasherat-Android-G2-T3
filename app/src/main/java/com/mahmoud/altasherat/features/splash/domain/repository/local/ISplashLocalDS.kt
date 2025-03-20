package com.mahmoud.altasherat.features.splash.domain.repository.local

internal interface ISplashLocalDS {
    suspend fun getUserAccessToken(): String
}