package com.mahmoud.altasherat.features.home.visa_requests.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.common.util.Constants
import com.mahmoud.altasherat.common.util.Constants.CONTENT_TYPE_JSON
import com.mahmoud.altasherat.common.util.Constants.HEADER_ACCEPT
import com.mahmoud.altasherat.features.home.visa_requests.data.models.dto.TourismVisaRequestsResponseDto
import com.mahmoud.altasherat.features.home.visa_requests.domain.repository.remote.ITourismVisaRequestsRemoteDS

class TourismVisaRequestsRemoteDS(
    private val restApiNetworkProvider: IRestApiNetworkProvider
): ITourismVisaRequestsRemoteDS {

    override suspend fun getTourismVisaRequests(): TourismVisaRequestsResponseDto {
        return restApiNetworkProvider.get(
            endpoint = Constants.TOURISM_VISAS_ENDPOINT,
            headers = mapOf(HEADER_ACCEPT to CONTENT_TYPE_JSON),
            responseType = TourismVisaRequestsResponseDto::class
        )

    }
}