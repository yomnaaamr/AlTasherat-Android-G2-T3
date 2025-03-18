package com.mahmoud.altasherat.features.splash.data.repository

import com.mahmoud.altasherat.features.splash.data.mappers.SplashResponseMapper
import com.mahmoud.altasherat.features.splash.domain.models.SplashResponse
import com.mahmoud.altasherat.features.splash.domain.repository.ISplashRepository
import com.mahmoud.altasherat.features.splash.domain.repository.local.ISplashLocalDS
import com.mahmoud.altasherat.features.splash.domain.repository.remote.ISplashRemoteDS

internal class SplashRepository(
    private val remoteDS: ISplashRemoteDS,
    private val localDS: ISplashLocalDS
): ISplashRepository {

    override suspend fun getCountries(): SplashResponse {
        val response = remoteDS.getCountries()
        return SplashResponseMapper.dtoToDomain(response)
    }

    override suspend fun savaCountry(splashResponse: SplashResponse) {
        val result = SplashResponseMapper.domainToEntity(splashResponse)
        localDS.savaCountry(result)
    }

}