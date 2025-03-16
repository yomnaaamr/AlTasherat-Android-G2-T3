package com.mahmoud.altasherat.features.language_country.presentation

import com.mahmoud.altasherat.common.domain.models.ListItem

sealed interface LanguageAction {

    data class SaveSelections(val selectedLanguage: ListItem, val selectedCountry: ListItem) :
        LanguageAction

}