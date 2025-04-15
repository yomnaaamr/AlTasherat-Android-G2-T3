package com.mahmoud.altasherat.features.home.visa_requests.domain.repository.remote

import com.mahmoud.altasherat.features.home.visa_requests.data.models.dto.TourismVisaRequestsResponseDto

interface ITourismVisaRequestsRemoteDS {

    suspend fun getTourismVisaRequests(languageCode: String): TourismVisaRequestsResponseDto
}