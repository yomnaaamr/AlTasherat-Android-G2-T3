package com.mahmoud.altasherat.features.onBoarding.domain.useCase

import androidx.datastore.core.IOException
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.LocalStorageError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.onBoarding.domain.repository.IOnBoardingRepository
import javax.inject.Inject

class IsFirstTimeToLaunchTheAppUC(private val repository: IOnBoardingRepository) {
    suspend operator fun invoke(): Boolean {
        return try {
            repository.isFirstTimeToLaunchTheApp()
        }catch (ioException: IOException){
            throw AltasheratException(LocalStorageError.IO_ERROR)
        }catch (e: Exception){
            throw AltasheratException(AltasheratError.UnknownError(e.message!!))
        }
    }
}
