package com.mahmoud.altasherat.onBoarding.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.mahmoud.altasherat.onBoarding.domain.useCase.GetOnBoardingVisibilityUC
import com.mahmoud.altasherat.onBoarding.domain.useCase.SaveOnBoardingVisibilityUC

class OnBoardingViewModel (
    private val saveOnBoardingVisibilityUC: SaveOnBoardingVisibilityUC,
):ViewModel() {

    fun setOnBoardingVisibilityShown (){
        saveOnBoardingVisibilityUC()
    }

}