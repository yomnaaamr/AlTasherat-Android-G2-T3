package com.mahmoud.altasherat.features.onBoarding.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.onError
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetCountriesFromLocalUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.SaveSelectionsUC
import com.mahmoud.altasherat.features.onBoarding.domain.useCase.SetOnBoardingStateUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC,
    private val saveSelectionsUC: SaveSelectionsUC,
    private val setOnBoardingStateUC: SetOnBoardingStateUC,
    ) : ViewModel() {


    private val _state = MutableStateFlow<LanguageContract.LanguageState>(LanguageContract.LanguageState.Idle)
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
        }
    }


    init {
        getCountries()
    }

    private fun saveSelections(selectedLanguage: Language, selectedCountry: Country) {
        viewModelScope.launch {
            saveSelectionsUC(selectedLanguage, selectedCountry)
                .onSuccess {
                    _events.send(LanguageContract.LanguageEvent.NavigationToAuth)
                }
                .onError {
                    _events.send(LanguageContract.LanguageEvent.Error(it))
                }
        }
    }

    private fun setOnBoardingVisibilityShown() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val result = setOnBoardingStateUC()) {
                    is Resource.Error -> Log.e("OnBoardingError", result.error.toString())
                    is Resource.Loading -> Log.d("OnBoardingState", "loading...")
                    is Resource.Success -> {
                        Log.d("OnBoardingState", "setOnBoardingVisibilityShown $result")
                    }
                }
            }


        }
    }


    private fun getCountries() {
        viewModelScope.launch {
            getCountriesFromLocalUC()
                .onSuccess { countries ->
                    _state.value = LanguageContract.LanguageState.Success(countries)
                }
                .onError { error ->
                    _state.value = LanguageContract.LanguageState.Error(error)
                    _events.send(LanguageContract.LanguageEvent.Error(error))
                }
        }
    }

}