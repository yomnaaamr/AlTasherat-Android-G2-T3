package com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Countries
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.ILanguageCountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class GetCountriesFromRemoteUC(private val repository: ILanguageCountryRepository) {

    operator fun invoke(): Flow<Resource<Countries>> =
        flow {
            emit(Resource.Loading)
            val splashResponse = repository.getCountriesFromRemote()
            repository.savaCountriesToLocal(splashResponse)
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