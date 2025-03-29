package com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.common.util.Constants.CONTENT_TYPE_JSON
import com.mahmoud.altasherat.common.util.Constants.COUNTRIES_ENDPOINT
import com.mahmoud.altasherat.common.util.Constants.HEADER_ACCEPT
import com.mahmoud.altasherat.common.util.Constants.HEADER_X_LOCALE
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.models.dto.CountriesDto
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.remote.ILanguageCountryRemoteDS

internal class LanguageCountryRemoteDS(
    private val restApiNetworkProvider: IRestApiNetworkProvider
) : ILanguageCountryRemoteDS {

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