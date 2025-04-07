package com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.ICountryRepository
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.repository.ILanguageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SaveSelectionsUC(
    private val countryRepository: ICountryRepository,
    private val languageRepository: ILanguageRepository
) {

    operator fun invoke(
        selectedLanguage: Language,
        selectedCountry: Country): Flow<Resource<Unit>> =

        flow {
            emit(Resource.Loading)
            languageRepository.saveSelectedLanguage(selectedLanguage)
            countryRepository.saveSelectedCountry(selectedCountry)
            emit(Resource.Success(Unit))
        }.catch { throwable ->
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError(
                        "Unknown error in SaveSelectionsUC: $throwable"
                    )
                )
            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)


}