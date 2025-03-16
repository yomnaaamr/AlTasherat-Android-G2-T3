package com.mahmoud.altasherat.features.onBoarding.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.features.onBoarding.domain.useCase.SetOnBoardingAsShownUC
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val saveOnBoardingVisibilityUC: SetOnBoardingAsShownUC,
) : ViewModel() {

    fun setOnBoardingVisibilityShown() {
        viewModelScope.launch {
            saveOnBoardingVisibilityUC()
        }
    }

}