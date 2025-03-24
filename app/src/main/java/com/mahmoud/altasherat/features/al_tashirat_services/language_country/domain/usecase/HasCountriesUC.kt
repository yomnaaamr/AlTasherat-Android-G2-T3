package com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.ILanguageCountryRepository

class HasCountriesUC(private val repository: ILanguageCountryRepository) {

    suspend operator fun invoke(): Resource<Boolean> {
        return try {
            Resource.Success(repository.hasCountries())
        } catch (throwable: Throwable) {
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError("Unknown error in HasCountriesUC: $throwable")
                )
            Resource.Error(failureResource.error)
        }
    }

}