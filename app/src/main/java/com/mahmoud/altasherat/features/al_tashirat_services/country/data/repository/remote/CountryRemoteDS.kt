package com.mahmoud.altasherat.features.al_tashirat_services.country.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.common.util.Constants.CONTENT_TYPE_JSON
import com.mahmoud.altasherat.common.util.Constants.COUNTRIES_ENDPOINT
import com.mahmoud.altasherat.common.util.Constants.HEADER_ACCEPT
import com.mahmoud.altasherat.common.util.Constants.HEADER_X_LOCALE
import com.mahmoud.altasherat.features.al_tashirat_services.country.data.models.dto.CountriesDto
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.remote.ICountryRemoteDS

internal class CountryRemoteDS(
    private val restApiNetworkProvider: IRestApiNetworkProvider
) : ICountryRemoteDS {

    override suspend fun getCountries(languageCode: String): CountriesDto {

        return restApiNetworkProvider.get(
            endpoint = COUNTRIES_ENDPOINT,
            headers = mapOf(
                HEADER_ACCEPT to CONTENT_TYPE_JSON,
                HEADER_X_LOCALE to languageCode
            ),
            responseType = CountriesDto::class
        )

    }

}