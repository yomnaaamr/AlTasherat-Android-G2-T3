package com.mahmoud.altasherat.features.splash.domain.repository.remote

import com.mahmoud.altasherat.features.splash.data.models.dto.SplashResponseDto

internal interface ISplashRemoteDS {
    suspend fun getCountries(): SplashResponseDto
}