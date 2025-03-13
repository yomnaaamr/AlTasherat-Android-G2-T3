package com.mahmoud.altasherat.features.splash.domain.repository.local

import com.mahmoud.altasherat.features.splash.data.models.entity.SplashEntity

internal interface ISplashLocalDS {
    suspend fun savaCountry(splashEntity: SplashEntity)
}