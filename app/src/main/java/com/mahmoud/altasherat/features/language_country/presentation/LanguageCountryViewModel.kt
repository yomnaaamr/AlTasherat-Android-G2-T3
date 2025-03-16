package com.mahmoud.altasherat.features.language_country.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.features.language_country.domain.usecase.GetCountriesUC
import com.mahmoud.altasherat.features.splash.domain.models.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LanguageCountryViewModel @Inject constructor(
    private val getCountriesUC: GetCountriesUC,
): ViewModel() {

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries.asStateFlow()


    init {
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch {
            _countries.value = getCountriesUC()
        }

    }


}