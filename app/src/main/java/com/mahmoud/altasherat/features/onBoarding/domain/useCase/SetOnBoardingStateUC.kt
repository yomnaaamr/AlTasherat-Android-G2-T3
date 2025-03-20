package com.mahmoud.altasherat.features.onBoarding.domain.useCase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.LocalStorageError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.onBoarding.domain.repository.IOnBoardingRepository

class SetOnBoardingStateUC(private val repository: IOnBoardingRepository) {

    suspend operator fun invoke(): Resource<Unit> {
        return try {
            Resource.Success(repository.saveOnBoardingState())
        } catch (e: java.io.IOException) {
            Resource.Error(LocalStorageError.IO_ERROR)
        } catch (e: IllegalStateException) {
            Resource.Error(LocalStorageError.DATA_CORRUPTION)
        } catch (throwable: Throwable) {
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError("Unknown error: $throwable")
                )
            Resource.Error(failureResource.error)
        }
    }

}

