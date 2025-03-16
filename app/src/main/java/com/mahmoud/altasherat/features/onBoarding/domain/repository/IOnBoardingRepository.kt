package com.mahmoud.altasherat.features.onBoarding.domain.repository

interface IOnBoardingRepository {
    suspend fun saveOnBoardingShown()
    suspend fun isFirstTimeToLaunchTheApp(): Boolean

}