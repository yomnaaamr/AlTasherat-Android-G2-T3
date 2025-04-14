package com.mahmoud.altasherat.features.home.visa_requests.data.repository

import com.mahmoud.altasherat.features.home.visa_requests.data.mappers.TourismVisaRequestResponseMapper
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequest
import com.mahmoud.altasherat.features.home.visa_requests.domain.repository.ITourismVisaRequestsRepository
import com.mahmoud.altasherat.features.home.visa_requests.domain.repository.remote.ITourismVisaRequestsRemoteDS

class TourismVisaRequestsRepository(
    private val remoteDataSource: ITourismVisaRequestsRemoteDS,
) : ITourismVisaRequestsRepository {
    override suspend fun getTourismVisaRequests(): List<TourismVisaRequest> {
        val response = remoteDataSource.getTourismVisaRequests()
        return TourismVisaRequestResponseMapper.dtoToDomain(response).visaRequests
    }
}