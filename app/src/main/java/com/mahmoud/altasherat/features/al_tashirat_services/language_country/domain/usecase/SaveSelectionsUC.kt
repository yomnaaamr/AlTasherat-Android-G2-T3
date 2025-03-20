package com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase

import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.LocalStorageError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.ILanguageCountryRepository
import java.io.IOException

class SaveSelectionsUC(
    private val repository: ILanguageCountryRepository
) {

    suspend operator fun invoke(
        selectedLanguage: Language,
        selectedCountry: Country
    ): Resource<Unit> {
        return try {
            Resource.Success(repository.saveSelections(selectedLanguage, selectedCountry))
        } catch (e: IOException) {
            Resource.Error(LocalStorageError.IO_ERROR)
        } catch (e: IllegalStateException) {
            Resource.Error(LocalStorageError.DATA_CORRUPTION)
        } catch (throwable: Throwable) {
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError("Unknown error in SaveSelectionsUC: $throwable")
                )
            Resource.Error(failureResource.error)
        }
    }

}