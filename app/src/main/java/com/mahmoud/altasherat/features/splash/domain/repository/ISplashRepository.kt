package com.mahmoud.altasherat.features.splash.domain.repository

import com.mahmoud.altasherat.features.splash.domain.models.SplashResponse

interface ISplashRepository {
    suspend fun getCountries(): SplashResponse
    suspend fun savaCountry(splashResponse: SplashResponse)

}