package com.mahmoud.altasherat.features.menu_options.change_language.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language

class ChangeLanguageContract {

    sealed interface ChangeLanguageAction {
        data class SaveSelectedLanguage(val selectedLanguage: Language) : ChangeLanguageAction
    }

    sealed interface ChangeLanguageEvent {
        data class Error(val error: AltasheratError) : ChangeLanguageEvent
        data object NavigationToProfile : ChangeLanguageEvent

    }

    sealed class ChangeLanguageState {
        data object Idle : ChangeLanguageState()
        data object Loading : ChangeLanguageState()
        data class Success(val data: List<Country>) : ChangeLanguageState()
        data class Error(val altasheratError: AltasheratError) : ChangeLanguageState()

    }
}