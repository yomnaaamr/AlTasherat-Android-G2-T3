package com.mahmoud.altasherat.features.onBoarding.choose_language.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.onEachErrorSuspend
import com.mahmoud.altasherat.common.domain.util.onEachLoadingSuspend
import com.mahmoud.altasherat.common.domain.util.onEachSuccessSuspend
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.GetCountriesFromLocalUC
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.GetCountriesFromRemoteUC
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.SaveSelectionsUC
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language
import com.mahmoud.altasherat.features.onBoarding.onboarding.domain.useCase.SetOnBoardingStateUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC,
    private val saveSelectionsUC: SaveSelectionsUC,
    private val setOnBoardingStateUC: SetOnBoardingStateUC,
    private val getCountriesFromRemoteUC: GetCountriesFromRemoteUC,
) : ViewModel() {


    private val _state =
        MutableStateFlow<LanguageContract.LanguageState>(LanguageContract.LanguageState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<LanguageContract.LanguageEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: LanguageContract.LanguageAction) {
        when (action) {
            is LanguageContract.LanguageAction.SaveSelections -> {
                saveSelections(action.selectedLanguage, action.selectedCountry)
            }

            is LanguageContract.LanguageAction.SetOnBoardingState -> {
                setOnBoardingVisibilityShown()
            }

            is LanguageContract.LanguageAction.GetCountriesFromRemote -> {
                fetchCountries(action.languageCode)
            }

        }
    }


    init {
        getCountries()
    }

    private fun saveSelections(selectedLanguage: Language, selectedCountry: Country) {

        saveSelectionsUC(selectedLanguage, selectedCountry)
            .onEachSuccessSuspend {
                _events.send(LanguageContract.LanguageEvent.NavigationToOnBoarding)
            }
            .onEachErrorSuspend {
                _events.send(LanguageContract.LanguageEvent.Error(it))
            }.launchIn(viewModelScope)

    }

    private fun setOnBoardingVisibilityShown() {

        setOnBoardingStateUC()
            .onEachSuccessSuspend {  }
            .onEachErrorSuspend {
                _events.send(LanguageContract.LanguageEvent.Error(it))
            }     .launchIn(viewModelScope)
    }


    private fun getCountries() {

        getCountriesFromLocalUC()
            .onEachSuccessSuspend { countries ->
                _state.value = LanguageContract.LanguageState.Success(countries)
            }
            .onEachErrorSuspend {
                _state.value = LanguageContract.LanguageState.Error(it)
                _events.send(LanguageContract.LanguageEvent.Error(it))
            }
            .launchIn(viewModelScope)

    }


    private fun fetchCountries(languageCode: String) {
        getCountriesFromRemoteUC(languageCode)
            .onEachSuccessSuspend { countries ->
                _state.value = LanguageContract.LanguageState.Success(countries.data)

            }
            .onEachErrorSuspend {
                _events.send(LanguageContract.LanguageEvent.Error(it))
            }
            .onEachLoadingSuspend {
                _state.value = LanguageContract.LanguageState.Loading
            }.launchIn(viewModelScope)

    }

}