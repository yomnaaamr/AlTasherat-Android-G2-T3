package com.mahmoud.altasherat.features.al_tashirat_services.language.data.repository

import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.repository.ILanguageRepository
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.repository.local.ILanguageLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language

class LanguageRepository(
    private val localDS: ILanguageLocalDS
): ILanguageRepository {


    override suspend fun getLanguageCode(): String? {
        return localDS.getLanguageCode()
    }

    override suspend fun saveSelectedLanguage(selectedLanguage: Language) {
        localDS.saveSelectedLanguage(selectedLanguage)
    }

}