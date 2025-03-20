package com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.models.dto.CountriesDto
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.remote.ILanguageCountryRemoteDS

internal class LanguageCountryRemoteDS(
    private val restApiNetworkProvider: IRestApiNetworkProvider
) : ILanguageCountryRemoteDS {

    override suspend fun getCountries(): CountriesDto {

        return restApiNetworkProvider.get(
            endpoint = COUNTRIES_ENDPOINT,
            headers = mapOf(
                HEADER_ACCEPT to CONTENT_TYPE_JSON,
                HEADER_X_LOCALE to LOCALE_EN
            ),
            responseType = CountriesDto::class
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