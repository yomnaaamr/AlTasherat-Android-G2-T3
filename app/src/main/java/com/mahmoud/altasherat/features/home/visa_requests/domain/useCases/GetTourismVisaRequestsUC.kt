package com.mahmoud.altasherat.features.home.visa_requests.domain.useCases

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequests
import com.mahmoud.altasherat.features.home.visa_requests.domain.repository.ITourismVisaRequestsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTourismVisaRequestsUC(
    private val tourismVisaRequestsRepository: ITourismVisaRequestsRepository,
) {


    operator fun invoke(languageCode: String): Flow<Resource<TourismVisaRequests>> {
        return flow {
            emit(Resource.Loading)
            val response = tourismVisaRequestsRepository.getTourismVisaRequests(languageCode)
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