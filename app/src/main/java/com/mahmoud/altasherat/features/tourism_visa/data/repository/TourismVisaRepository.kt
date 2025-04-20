package com.mahmoud.altasherat.features.tourism_visa.data.repository

import com.mahmoud.altasherat.features.tourism_visa.data.mappers.TourismVisaResponseMapper
import com.mahmoud.altasherat.features.tourism_visa.data.models.request.StoreTourismVisaRequest
import com.mahmoud.altasherat.features.tourism_visa.domain.models.TourismVisaResponse
import com.mahmoud.altasherat.features.tourism_visa.domain.repository.ITourismVisaRepository
import com.mahmoud.altasherat.features.tourism_visa.domain.repository.remote.ITourismVisaRemoteDS

class TourismVisaRepository(
    private val remoteDS: ITourismVisaRemoteDS
) : ITourismVisaRepository{
    override suspend fun storeTourismVisa(request: StoreTourismVisaRequest): TourismVisaResponse {
        return TourismVisaResponseMapper.dtoToDomain(remoteDS.storeTourismVisa(request))
    }
}