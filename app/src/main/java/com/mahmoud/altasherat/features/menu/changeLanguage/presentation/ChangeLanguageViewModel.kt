package com.mahmoud.altasherat.features.menu.changeLanguage.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.onError
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.SaveSelectedLanguageUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChangeLanguageViewModel @Inject constructor(
    private val saveSelectedLanguageUC: SaveSelectedLanguageUC
) : ViewModel() {


    private val _state =
        MutableStateFlow<ChangeLanguageContract.ChangeLanguageState>(ChangeLanguageContract.ChangeLanguageState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<ChangeLanguageContract.ChangeLanguageEvent>()
    val events = _events.receiveAsFlow()


    fun onAction(action: ChangeLanguageContract.ChangeLanguageAction) {
        when (action) {
            is ChangeLanguageContract.ChangeLanguageAction.SaveSelectedLanguage -> {
                saveSelectedLanguage(action.selectedLanguage)
            }
        }
    }


    private fun saveSelectedLanguage(selectedLanguage: Language) {
        viewModelScope.launch {
            saveSelectedLanguageUC(selectedLanguage)
                .onSuccess {
                    _events.send(ChangeLanguageContract.ChangeLanguageEvent.NavigationToProfile)
                }
                .onError {
                    _events.send(ChangeLanguageContract.ChangeLanguageEvent.Error(it))
                }
        }
    }
}