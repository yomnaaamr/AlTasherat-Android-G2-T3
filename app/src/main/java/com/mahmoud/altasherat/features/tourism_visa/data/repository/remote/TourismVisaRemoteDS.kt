package com.mahmoud.altasherat.features.tourism_visa.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.common.util.Constants.AUTHORIZATION
import com.mahmoud.altasherat.common.util.Constants.CONTENT_TYPE_JSON
import com.mahmoud.altasherat.common.util.Constants.HEADER_ACCEPT
import com.mahmoud.altasherat.common.util.Constants.STORE_TOURISM_VISA_ENDPOINT
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.user.util.toAttachmentsPart
import com.mahmoud.altasherat.features.al_tashirat_services.user.util.toPassportImagePart
import com.mahmoud.altasherat.features.tourism_visa.data.models.dto.TourismVisaResponseDto
import com.mahmoud.altasherat.features.tourism_visa.data.models.request.StoreTourismVisaRequest
import com.mahmoud.altasherat.features.tourism_visa.domain.repository.remote.ITourismVisaRemoteDS

class TourismVisaRemoteDS(
    private val restApiNetworkProvider: IRestApiNetworkProvider,
    private val userLocalDS: IUserInfoLocalDS
) : ITourismVisaRemoteDS {
    override suspend fun storeTourismVisa(request: StoreTourismVisaRequest): TourismVisaResponseDto {
        val requestMap = request.createPartMap()
        val imageParts = request.passportImages.map { it -> it.toPassportImagePart() }
        val attachmentsParts = request.attachments.map { it -> it.toAttachmentsPart() }
        val allFiles = imageParts + attachmentsParts
        return restApiNetworkProvider.postFiles(
            endpoint = STORE_TOURISM_VISA_ENDPOINT,
            files = allFiles,
            data = requestMap,
            headers = mapOf(
                HEADER_ACCEPT to CONTENT_TYPE_JSON,
                AUTHORIZATION to "Bearer ${userLocalDS.getUserAccessToken()}"
            ),
            responseType = TourismVisaResponseDto::class
        )
    }
}