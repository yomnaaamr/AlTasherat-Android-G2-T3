package com.mahmoud.altasherat.onBoarding.domain.local

interface IOnBoardingLocalDS {
    suspend fun setOnBoardingShown()
    suspend fun getOnBoardingVisibility():Boolean

}