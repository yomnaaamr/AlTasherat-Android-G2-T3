package com.mahmoud.altasherat.features.onBoarding.presentation

import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language

sealed interface LanguageAction {

    data class SaveSelections(val selectedLanguage: Language, val selectedCountry: Country) :
        LanguageAction

}