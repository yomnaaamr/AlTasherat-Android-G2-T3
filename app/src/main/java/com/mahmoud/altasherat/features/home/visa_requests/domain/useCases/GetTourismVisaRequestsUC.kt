package com.mahmoud.altasherat.features.home.visa_requests.domain.useCases

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequest
import com.mahmoud.altasherat.features.home.visa_requests.domain.repository.ITourismVisaRequestsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTourismVisaRequestsUC(
    private val tourismVisaRequestsRepository: ITourismVisaRequestsRepository,
) {


    operator fun invoke(): Flow<Resource<List<TourismVisaRequest>>> {
        return flow {
            emit(Resource.Loading)
            val response = tourismVisaRequestsRepository.getTourismVisaRequests()
            emit(Resource.Success(response))

        }.catch { throwable ->
            val failureResource =
                if (throwable is AltasheratException) throwable else AltasheratException(
                    AltasheratError.UnknownError(
                        "Unknown error in GetTourismVisaRequestsUC: $throwable"
                    )
                )
            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)
    }

}