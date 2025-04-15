package com.mahmoud.altasherat.features.al_tashirat_services.language.data

import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language


internal object MockLanguageData {
    val language = Language(
        0, "Arabic", code = "ar", flag = "🇸🇦",
        isSelected = false,
        phoneCode = "+20"
    )

}