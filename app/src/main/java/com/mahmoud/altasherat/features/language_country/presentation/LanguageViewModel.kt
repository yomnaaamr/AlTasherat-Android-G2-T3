package com.mahmoud.altasherat.features.language_country.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.common.domain.models.ListItem
import com.mahmoud.altasherat.features.language_country.domain.usecase.GetCountriesUC
import com.mahmoud.altasherat.features.language_country.domain.usecase.SaveSelectionsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getCountriesUC: GetCountriesUC,
    private val saveSelectionsUC: SaveSelectionsUC
): ViewModel() {

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries.asStateFlow()


    fun onAction(action: LanguageAction) {
        when (action) {
            is LanguageAction.SaveSelections -> {
                saveSelections(action.selectedLanguage, action.selectedCountry)
            }
        }
    }

    private fun saveSelections(selectedLanguage: ListItem, selectedCountry: ListItem) {
        viewModelScope.launch {
            saveSelectionsUC(selectedLanguage, selectedCountry)
        }
    }

    init {
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch {
            _countries.value = getCountriesUC()
        }
    }




}