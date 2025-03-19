package com.mahmoud.altasherat.common.presentation.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

fun Context.changeLocale(languageCode: String) {
    val locale = Locale.forLanguageTag(languageCode)
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(locale.toLanguageTag()))
}