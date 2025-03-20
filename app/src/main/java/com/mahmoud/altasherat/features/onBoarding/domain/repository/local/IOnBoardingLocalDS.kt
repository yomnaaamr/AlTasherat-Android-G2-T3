package com.mahmoud.altasherat.features.onBoarding.domain.repository.local

interface IOnBoardingLocalDS {
    suspend fun setOnBoardingShown()
    suspend fun isFirstTimeToLaunchTheApp():Boolean

}