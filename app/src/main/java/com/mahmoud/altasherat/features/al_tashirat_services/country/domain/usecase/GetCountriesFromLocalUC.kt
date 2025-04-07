package com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.LocalStorageError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.ICountryRepository
import java.io.IOException

class GetCountriesFromLocalUC(private val repository: ICountryRepository) {

    suspend operator fun invoke(): Resource<List<Country>> {

        return try {
            val countries = repository.getCountriesFromLocal()
             Resource.Success(countries)
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