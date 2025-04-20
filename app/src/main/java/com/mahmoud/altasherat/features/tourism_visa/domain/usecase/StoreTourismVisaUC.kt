package com.mahmoud.altasherat.features.tourism_visa.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.tourism_visa.data.models.request.StoreTourismVisaRequest
import com.mahmoud.altasherat.features.tourism_visa.domain.models.TourismVisaResponse
import com.mahmoud.altasherat.features.tourism_visa.domain.repository.ITourismVisaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StoreTourismVisaUC (
    private val repository: ITourismVisaRepository
) {
    operator fun invoke(tourismVisaRequest: StoreTourismVisaRequest): Flow<Resource<TourismVisaResponse>> =
        flow {
            emit(Resource.Loading)

            tourismVisaRequest.validateRequest()
                .onSuccess { errors ->
                    if (errors.isNotEmpty()) {
                        throw AltasheratException(AltasheratError.ValidationErrors(errors))
                    }
                }

            val response = repository.storeTourismVisa(tourismVisaRequest)
            emit(Resource.Success(response))

        }.catch { throwable ->
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError(
                        "Unknown error in UpdateUserInfoUC: $throwable"
                    )
                )

            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)
}