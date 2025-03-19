package com.mahmoud.altasherat.features.onBoarding.domain.local

interface IOnBoardingLocalDS {
    suspend fun setOnBoardingShown()
    suspend fun isFirstTimeToLaunchTheApp():Boolean

}