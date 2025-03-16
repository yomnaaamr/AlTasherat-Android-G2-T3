package com.mahmoud.altasherat.common.data

import com.mahmoud.altasherat.common.domain.models.Language

object LanguageDataSource {

    fun getLanguages(): List<Language> = listOf(
        Language(0, "Arabic", code = "ar", flag = "🇸🇦"),
        Language(1, "English", code = "en", flag = "🇬🇧")
    )

}