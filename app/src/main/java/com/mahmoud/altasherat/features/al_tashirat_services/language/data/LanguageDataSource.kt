package com.mahmoud.altasherat.features.al_tashirat_services.language.data

import android.content.Context
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language

object LanguageDataSource {

    fun getLanguages(context: Context): List<Language> = listOf(
        Language(
            0, context.getString(R.string.arabic_language), code = "ar", flag = "🇸🇦",
            phoneCode = "00966",
        ),
        Language(1, context.getString(R.string.english_language), code = "en", flag = "🇬🇧", phoneCode = "0020")
    )

}