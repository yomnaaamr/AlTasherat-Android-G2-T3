package com.mahmoud.altasherat.features.splash.domain.usecase

import android.util.Log
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.splash.domain.models.SplashResponse
import com.mahmoud.altasherat.features.splash.domain.repository.ISplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class FetchCountriesUC(private val repository: ISplashRepository) {

    operator fun invoke(): Flow<Resource<SplashResponse>> =
        flow {
            emit(Resource.Loading)
            val splashResponse = repository.getCountries()
            repository.savaCountry(splashResponse)
            emit(Resource.Success(splashResponse))

        }.catch { throwable ->
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError(
                        "Unknown error in GetCountriesUC: $throwable"
                    )
                )
            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)
}