package com.mahmoud.altasherat.features.home.visa_requests.data.mappers

import com.mahmoud.altasherat.features.home.visa_requests.data.models.dto.TourismVisaRequestsResponseDto
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequests

object TourismVisaRequestResponseMapper {

    fun dtoToDomain(input: TourismVisaRequestsResponseDto): TourismVisaRequests {
        return TourismVisaRequests(
            visaRequests = input.data?.map { TourismVisaRequestMapper.dtoToDomain(it) } ?: emptyList()
        )
    }
}