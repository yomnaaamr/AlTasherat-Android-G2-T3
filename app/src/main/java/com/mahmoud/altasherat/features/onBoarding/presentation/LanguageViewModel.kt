package com.mahmoud.altasherat.features.onBoarding.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetCountriesFromLocalUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.SaveSelectionsUC
import com.mahmoud.altasherat.features.onBoarding.domain.useCase.SetOnBoardingStateUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC,
    private val saveSelectionsUC: SaveSelectionsUC,
    private val saveOnBoardingVisibilityUC: SetOnBoardingStateUC,

    ): ViewModel() {

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries.asStateFlow()



    fun setOnBoardingVisibilityShown() {
        viewModelScope.launch {
            when (val result = saveOnBoardingVisibilityUC()) {
                is Resource.Error -> Log.e("OnBoardingError", result.error.toString())
                is Resource.Loading -> Log.d("onBoardingLoading", "loading...")
                is Resource.Success-> {
                    Log.d("AITASHERAT", "setOnBoardingVisibilityShown $result")
                }
            }
        }
    }

    fun onAction(action: LanguageAction) {
        when (action) {
            is LanguageAction.SaveSelections -> {
                saveSelections(action.selectedLanguage, action.selectedCountry)
            }
        }
    }

    private fun saveSelections(selectedLanguage: Language, selectedCountry: Country) {
        viewModelScope.launch {
            saveSelectionsUC(selectedLanguage, selectedCountry)
        }
    }

    init {
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch {
            getCountriesFromLocalUC()
                .onSuccess { countries ->
                    _countries.value = countries
                }
        }
    }

}