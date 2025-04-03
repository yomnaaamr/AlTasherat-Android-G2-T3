package com.mahmoud.altasherat.features.onBoarding.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language

class LanguageContract {

    sealed interface LanguageAction {
        data class SaveSelections(val selectedLanguage: Language, val selectedCountry: Country) :
            LanguageAction

        data object SetOnBoardingState : LanguageAction
        data class GetCountriesFromRemote(val languageCode: String) : LanguageAction
    }

    sealed interface LanguageEvent {
        data class Error(val error: AltasheratError) : LanguageEvent
        data object NavigationToOnBoarding : LanguageEvent

    }

    sealed class LanguageState {
        data object Idle : LanguageState()
        data object Loading : LanguageState()
        data class Success(val data: List<Country>) : LanguageState()
        data class Error(val altasheratError: AltasheratError) : LanguageState()

    }
}