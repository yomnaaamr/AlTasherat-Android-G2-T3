package com.mahmoud.altasherat.features.tourism_visa.domain.repository

import com.mahmoud.altasherat.features.tourism_visa.data.models.request.StoreTourismVisaRequest
import com.mahmoud.altasherat.features.tourism_visa.domain.models.TourismVisaResponse

interface ITourismVisaRepository {
    suspend fun storeTourismVisa(request: StoreTourismVisaRequest): TourismVisaResponse
}