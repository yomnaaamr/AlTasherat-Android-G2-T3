package com.mahmoud.altasherat.features.home.visa_requests.data.repository

import com.mahmoud.altasherat.features.home.visa_requests.data.mappers.TourismVisaRequestResponseMapper
import com.mahmoud.altasherat.features.home.visa_requests.data.repository.remote.TourismVisaRequestsRemoteDS
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequest
import com.mahmoud.altasherat.features.home.visa_requests.domain.repository.ITourismVisaRequestsRepository

class TourismVisaRequestsRepository(
    private val remoteDataSource: TourismVisaRequestsRemoteDS,
) : ITourismVisaRequestsRepository {
    override suspend fun getTourismVisaRequests(): List<TourismVisaRequest> {
        val response = remoteDataSource.getTourismVisaRequests()
        return TourismVisaRequestResponseMapper.dtoToDomain(response).visaRequests
    }
}