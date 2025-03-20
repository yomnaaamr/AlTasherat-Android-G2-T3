package com.mahmoud.altasherat.features.onBoarding.domain.repository

interface IOnBoardingRepository {
    suspend fun saveOnBoardingState()
    suspend fun getOnBoardingState(): Boolean

}