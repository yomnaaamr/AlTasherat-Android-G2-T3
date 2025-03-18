package com.mahmoud.altasherat.features.onBoarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.onError
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.onBoarding.domain.useCase.IsFirstTimeToLaunchTheAppUC
import com.mahmoud.altasherat.features.onBoarding.domain.useCase.SetOnBoardingAsShownUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val saveOnBoardingVisibilityUC: SetOnBoardingAsShownUC,
    private val isFirstTimeToLaunchTheAppUC: IsFirstTimeToLaunchTheAppUC,
) : ViewModel() {

    fun setOnBoardingVisibilityShown() {
        viewModelScope.launch {
            saveOnBoardingVisibilityUC()
        }
    }

    suspend fun isFirstTimeToLaunchTheApp(): Boolean {
        isFirstTimeToLaunchTheAppUC().onSuccess { isFirstTime ->
            return isFirstTime
        }.onError { return false }
        return false
    }


}