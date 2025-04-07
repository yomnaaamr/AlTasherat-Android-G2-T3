package com.mahmoud.altasherat.features.al_tashirat_services.language.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.LocalStorageError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.repository.ILanguageRepository
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language
import java.io.IOException

class SaveSelectedLanguageUC(
    private val repository: ILanguageRepository
) {

    suspend operator fun invoke(
        selectedLanguage: Language,
    ): Resource<Unit> {
        return try {
            Resource.Success(repository.saveSelectedLanguage(selectedLanguage))
        } catch (e: IOException) {
            Resource.Error(LocalStorageError.IO_ERROR)
        } catch (e: IllegalStateException) {
            Resource.Error(LocalStorageError.DATA_CORRUPTION)
        } catch (throwable: Throwable) {
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError("Unknown error in SaveSelectedLanguageUC: $throwable")
                )
            Resource.Error(failureResource.error)
        }
    }
}