package com.mahmoud.altasherat.features.onBoarding.data.repository

import com.mahmoud.altasherat.features.onBoarding.domain.local.IOnBoardingLocalDS
import com.mahmoud.altasherat.features.onBoarding.domain.repository.IOnBoardingRepository
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(private val onBoardingLocalDS: IOnBoardingLocalDS):
    IOnBoardingRepository {
    override suspend fun saveOnBoardingShown() {
        onBoardingLocalDS.setOnBoardingShown()
    }

    override suspend fun isFirstTimeToLaunchTheApp(): Boolean {
        return onBoardingLocalDS.isFirstTimeToLaunchTheApp()
    }

}