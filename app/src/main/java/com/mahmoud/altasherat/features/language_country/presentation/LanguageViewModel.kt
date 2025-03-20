package com.mahmoud.altasherat.features.language_country.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.common.domain.models.Language
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.language_country.domain.usecase.GetCountriesUC
import com.mahmoud.altasherat.features.language_country.domain.usecase.SaveSelectionsUC
import com.mahmoud.altasherat.features.onBoarding.domain.useCase.SetOnBoardingAsShownUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getCountriesUC: GetCountriesUC,
    private val saveSelectionsUC: SaveSelectionsUC,
    private val saveOnBoardingVisibilityUC: SetOnBoardingAsShownUC,

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
            getCountriesUC()
                .onSuccess { countries ->
                    _countries.value = countries
                }
        }
    }

}