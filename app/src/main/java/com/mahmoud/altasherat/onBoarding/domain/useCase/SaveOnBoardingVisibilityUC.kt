package com.mahmoud.altasherat.onBoarding.domain.useCase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.Resource.Success
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.onBoarding.domain.repository.IOnBoardingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SaveOnBoardingVisibilityUC @Inject constructor(private val repository: IOnBoardingRepository) {
    operator fun invoke() = flow {
        emit(Resource.Loading)
        emit(Success(repository.saveOnBoardingShown()))
    }.catch {e:Throwable ->
        if (e is AltasheratException) throw e
        else throw AltasheratException(
            AltasheratError.UnknownError(
                "Unknown error in GetCountriesUC: $e"
            )
        )
    }.flowOn(Dispatchers.IO)
}
