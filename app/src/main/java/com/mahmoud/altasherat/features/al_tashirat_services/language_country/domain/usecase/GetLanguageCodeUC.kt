package com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.LocalStorageError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.ILanguageCountryRepository
import java.io.IOException

class GetLanguageCodeUC(
    private val repository: ILanguageCountryRepository
) {

    suspend operator fun invoke(): Resource<String?> {
        return try {
            Resource.Success(repository.getLanguageCode())
        } catch (e: IOException) {
            Resource.Error(LocalStorageError.IO_ERROR)
        } catch (throwable: Throwable) {
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError("Unknown error in GetLanguageCodeUC: $throwable")
                )
            Resource.Error(failureResource.error)
        }
    }
}