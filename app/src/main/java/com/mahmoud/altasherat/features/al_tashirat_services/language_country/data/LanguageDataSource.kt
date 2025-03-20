package com.mahmoud.altasherat.features.al_tashirat_services.language_country.data

import android.content.Context
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language

object LanguageDataSource {

    fun getLanguages(context: Context): List<Language> = listOf(
        Language(0, context.getString(R.string.arabic_language), code = "ar", flag = "🇸🇦"),
        Language(1, context.getString(R.string.english_language), code = "en", flag = "🇬🇧")
    )


}