package com.mahmoud.altasherat.onBoarding.data.repository

import com.mahmoud.altasherat.onBoarding.domain.local.IOnBoardingLocalDS
import com.mahmoud.altasherat.onBoarding.domain.repository.IOnBoardingRepository
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(private val onBoardingLocalDS: IOnBoardingLocalDS):
    IOnBoardingRepository {
    override suspend fun saveOnBoardingShown() {
        onBoardingLocalDS.setOnBoardingShown()
    }

    override suspend fun getOnBoardingVisibility(): Boolean {
        return onBoardingLocalDS.getOnBoardingVisibility()
    }

}