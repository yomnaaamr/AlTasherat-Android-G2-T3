package com.mahmoud.altasherat.features.splash.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.splash.data.models.dto.SplashResponseDto
import com.mahmoud.altasherat.features.splash.domain.repository.remote.ISplashRemoteDS

internal class SplashRemoteDS(
    private val restApiNetworkProvider: IRestApiNetworkProvider
) : ISplashRemoteDS {

    override suspend fun getCountries(): SplashResponseDto {

        return restApiNetworkProvider.get(
            endpoint = COUNTRIES_ENDPOINT,
            headers = mapOf(
                HEADER_ACCEPT to CONTENT_TYPE_JSON,
                HEADER_X_LOCALE to LOCALE_EN
            ),
            responseType = SplashResponseDto::class
        )

    }


    companion object {
        private const val COUNTRIES_ENDPOINT = "countries"
        private const val HEADER_ACCEPT = "Accept"
        private const val CONTENT_TYPE_JSON = "application/json"
        private const val HEADER_X_LOCALE = "X-locale"
        private const val LOCALE_EN = "en"
    }
}