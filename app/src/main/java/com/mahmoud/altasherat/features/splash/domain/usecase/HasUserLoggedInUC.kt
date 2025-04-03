package com.mahmoud.altasherat.features.splash.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.splash.domain.repository.ISplashRepository

class HasUserLoggedInUC(
    private val repository: ISplashRepository
) {
    suspend operator fun invoke(): Resource<Boolean> {
        return try {
            Resource.Success(repository.hasUserLoggedIn())
        } catch (throwable: Throwable) {
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError("Unknown error in HasUserLoggedInUC: $throwable")
                )
            Resource.Error(failureResource.error)
        }
    }
}