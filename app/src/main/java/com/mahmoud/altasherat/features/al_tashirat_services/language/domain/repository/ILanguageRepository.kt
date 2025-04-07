package com.mahmoud.altasherat.features.al_tashirat_services.language.domain.repository

import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language

interface ILanguageRepository {
    suspend fun getLanguageCode(): String?
    suspend fun saveSelectedLanguage(selectedLanguage: Language)
}