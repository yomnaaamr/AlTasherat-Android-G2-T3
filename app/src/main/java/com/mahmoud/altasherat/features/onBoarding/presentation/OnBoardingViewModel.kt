package com.mahmoud.altasherat.features.onBoarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
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
) : ViewModel() {

    fun setOnBoardingVisibilityShown() {
        viewModelScope.launch {
            when(val result = saveOnBoardingVisibilityUC())
            {
                is Resource.Error -> TODO()
                Resource.Loading -> TODO()
                is Resource.Success<*> -> TODO()
            }
        }
    }




}