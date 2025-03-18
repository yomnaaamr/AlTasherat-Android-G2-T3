package com.mahmoud.altasherat.features.language_country.domain.usecase

import java.io.IOException
import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.LocalStorageError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.language_country.domain.repository.ILanguageCountryRepository

class GetCountriesUC(private val repository: ILanguageCountryRepository) {

    suspend operator fun invoke(): Resource<List<Country>> {

        return try {
            Resource.Success(repository.getCountries())
        } catch (e: IOException) {
            Resource.Error(LocalStorageError.IO_ERROR)
        } catch (e: IllegalStateException) {
            Resource.Error(LocalStorageError.DATA_CORRUPTION)
        } catch (throwable: Throwable) {
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError("Unknown error in GetCountriesUC: $throwable")
                )
            Resource.Error(failureResource.error)
        }
    }

}