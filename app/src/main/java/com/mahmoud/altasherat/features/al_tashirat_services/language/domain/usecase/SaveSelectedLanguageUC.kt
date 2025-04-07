package com.mahmoud.altasherat.features.al_tashirat_services.language.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.repository.ILanguageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SaveSelectedLanguageUC(
    private val repository: ILanguageRepository
) {

    operator fun invoke(
        selectedLanguage: Language,
    ): Flow<Resource<Unit>> =

        flow {
            emit(Resource.Loading)
            emit(Resource.Success(repository.saveSelectedLanguage(selectedLanguage)))
        }.catch { throwable ->
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError(
                        "Unknown error in SaveSelectedLanguageUC: $throwable"
                    )
                )
            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)

}