package com.mahmoud.altasherat.features.onBoarding.onboarding.domain.useCase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.LocalStorageError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.onBoarding.onboarding.domain.repository.IOnBoardingRepository
import java.io.IOException

class GetOnBoardingStateUC(private val repository: IOnBoardingRepository) {
    suspend operator fun invoke(): Resource<Boolean> {
        return try {
            Resource.Success(repository.getOnBoardingState())
        } catch (e: IOException) {
            Resource.Error(LocalStorageError.IO_ERROR)
        } catch (throwable: Throwable) {
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError("Unknown error: $throwable")
                )
            Resource.Error(failureResource.error)
        }
    }
}
