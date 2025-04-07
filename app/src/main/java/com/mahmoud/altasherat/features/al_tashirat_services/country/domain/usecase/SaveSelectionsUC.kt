package com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase

import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.LocalStorageError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.ICountryRepository
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.repository.ILanguageRepository
import java.io.IOException

class SaveSelectionsUC(
    private val countryRepository: ICountryRepository,
    private val languageRepository: ILanguageRepository
) {

    suspend operator fun invoke(
        selectedLanguage: Language,
        selectedCountry: Country
    ): Resource<Unit> {
        return try {
            languageRepository.saveSelectedLanguage(selectedLanguage)
            countryRepository.saveSelectedCountry(selectedCountry)
            Resource.Success(Unit)
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