package com.mahmoud.altasherat.features.home.visa_requests.domain.repository

import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequest

interface ITourismVisaRequestsRepository {
    suspend fun getTourismVisaRequests(): List<TourismVisaRequest>

}