package com.mahmoud.altasherat.features.al_tashirat_services.language.data.repository.local

import com.google.gson.Gson
import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.repository.local.ILanguageLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language

class LanguageLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
    private val gson: Gson
): ILanguageLocalDS  {

    override suspend fun getLanguageCode(): String? {
        val selectedLanguageJson = localStorageProvider.get(StorageKeyEnum.SELECTED_LANGUAGE, "", String::class)
        if (selectedLanguageJson.isNotEmpty()) {
            val selectedLanguage = gson.fromJson(selectedLanguageJson, Language::class.java)
            return selectedLanguage.code
        }
        return null
    }


    override suspend fun saveSelectedLanguage(selectedLanguage: Language) {
        val selectedLanguageJson = gson.toJson(selectedLanguage)
        localStorageProvider.save(StorageKeyEnum.SELECTED_LANGUAGE, selectedLanguageJson, String::class)
    }
}