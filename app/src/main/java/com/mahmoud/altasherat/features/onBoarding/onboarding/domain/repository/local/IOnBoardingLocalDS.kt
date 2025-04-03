package com.mahmoud.altasherat.features.onBoarding.onboarding.domain.repository.local

interface IOnBoardingLocalDS {
    suspend fun setOnBoardingState()
    suspend fun getOnBoardingState(): Boolean

}