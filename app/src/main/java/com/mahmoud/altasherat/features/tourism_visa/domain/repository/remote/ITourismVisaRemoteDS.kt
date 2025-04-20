package com.mahmoud.altasherat.features.tourism_visa.domain.repository.remote

import com.mahmoud.altasherat.features.tourism_visa.data.models.dto.TourismVisaResponseDto
import com.mahmoud.altasherat.features.tourism_visa.data.models.request.StoreTourismVisaRequest

interface ITourismVisaRemoteDS {
    suspend fun storeTourismVisa(request: StoreTourismVisaRequest): TourismVisaResponseDto
}