package com.mahmoud.altasherat.features.onBoarding.onboarding.domain.repository

interface IOnBoardingRepository {
    suspend fun saveOnBoardingState()
    suspend fun getOnBoardingState(): Boolean

}