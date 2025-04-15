package com.mahmoud.altasherat.features.home.visa_requests.domain.repository

import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequests

interface ITourismVisaRequestsRepository {
    suspend fun getTourismVisaRequests(languageCode: String): TourismVisaRequests

}