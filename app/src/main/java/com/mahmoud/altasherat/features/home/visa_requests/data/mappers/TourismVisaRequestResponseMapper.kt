package com.mahmoud.altasherat.features.home.visa_requests.data.mappers

import com.mahmoud.altasherat.features.home.visa_requests.data.models.dto.TourismVisaRequestsResponseDto
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequests

object TourismVisaRequestResponseMapper {

    fun dtoToDomain(input: TourismVisaRequestsResponseDto): TourismVisaRequests {
        return TourismVisaRequests(
            message = input.message.orEmpty(),
            visaRequests = input.visaRequests?.map { TourismVisaRequestMapper.dtoToDomain(it) } ?: emptyList()
        )
    }
}