package com.mahmoud.altasherat.features.language_country.presentation

import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.common.domain.models.Language

sealed interface LanguageAction {

    data class SaveSelections(val selectedLanguage: Language, val selectedCountry: Country) :
        LanguageAction

}