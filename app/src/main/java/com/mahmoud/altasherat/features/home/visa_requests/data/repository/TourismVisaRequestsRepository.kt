package com.mahmoud.altasherat.features.home.visa_requests.data.repository

import com.mahmoud.altasherat.features.home.visa_requests.data.mappers.TourismVisaRequestResponseMapper
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequests
import com.mahmoud.altasherat.features.home.visa_requests.domain.repository.ITourismVisaRequestsRepository
import com.mahmoud.altasherat.features.home.visa_requests.domain.repository.remote.ITourismVisaRequestsRemoteDS

class TourismVisaRequestsRepository(
    private val remoteDataSource: ITourismVisaRequestsRemoteDS,
) : ITourismVisaRequestsRepository {
    override suspend fun getTourismVisaRequests(languageCode: String): TourismVisaRequests {
        val response = remoteDataSource.getTourismVisaRequests(languageCode)
        return TourismVisaRequestResponseMapper.dtoToDomain(response)
    }
}