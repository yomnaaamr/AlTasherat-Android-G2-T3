package com.mahmoud.altasherat.common.data

import android.content.Context
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.models.Language

object LanguageDataSource {

    fun getLanguages(context: Context): List<Language> = listOf(
        Language(0, context.getString(R.string.arabic_language), code = "ar", flag = "🇸🇦"),
        Language(1, context.getString(R.string.english_language), code = "en", flag = "🇬🇧")
    )


}