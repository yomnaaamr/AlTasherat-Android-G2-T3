package com.mahmoud.altasherat.features.al_tashirat_services.language.domain.repository.local

import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language

interface ILanguageLocalDS {
    suspend fun getLanguageCode(): String?
    suspend fun saveSelectedLanguage(selectedLanguage: Language)

}