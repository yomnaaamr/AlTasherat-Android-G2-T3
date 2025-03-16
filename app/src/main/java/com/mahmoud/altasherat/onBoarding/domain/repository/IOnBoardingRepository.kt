package com.mahmoud.altasherat.onBoarding.domain.repository

interface IOnBoardingRepository {
    suspend fun saveOnBoardingShown()
    suspend fun getOnBoardingVisibility(): Boolean

}