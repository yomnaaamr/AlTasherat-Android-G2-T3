package com.mahmoud.altasherat.features.onBoarding.onboarding.domain.useCase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.onBoarding.onboarding.domain.repository.IOnBoardingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SetOnBoardingStateUC(private val repository: IOnBoardingRepository) {

    operator fun invoke(): Flow<Resource<Unit>> =

        flow {
            emit(Resource.Loading)
            emit(Resource.Success(repository.saveOnBoardingState()))

        }.catch { throwable ->
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError(
                        "Unknown error in SetOnBoardingStateUC: $throwable"
                    )
                )
            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)


}

