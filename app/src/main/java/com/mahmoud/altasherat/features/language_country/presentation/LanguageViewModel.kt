package com.mahmoud.altasherat.features.language_country.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.features.onBoarding.domain.useCase.SetOnBoardingAsShownUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val setOnBoardingAsShownUC: SetOnBoardingAsShownUC,
) : ViewModel() {


    fun saveOnBoardingShown() {
        viewModelScope.launch {
            setOnBoardingAsShownUC()
        }
    }
}