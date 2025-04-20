package com.mahmoud.altasherat.features.splash.domain.repository

interface ISplashRepository {
    suspend fun getUserAccessToken(): String
    suspend fun hasUserLoggedIn(): Boolean

}