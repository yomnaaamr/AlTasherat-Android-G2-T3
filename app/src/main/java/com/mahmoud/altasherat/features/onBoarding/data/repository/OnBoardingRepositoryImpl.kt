package com.mahmoud.altasherat.features.onBoarding.data.repository

import com.mahmoud.altasherat.features.onBoarding.domain.repository.IOnBoardingRepository
import com.mahmoud.altasherat.features.onBoarding.domain.repository.local.IOnBoardingLocalDS

class OnBoardingRepositoryImpl(private val onBoardingLocalDS: IOnBoardingLocalDS) :
    IOnBoardingRepository {
    override suspend fun saveOnBoardingState() {
        onBoardingLocalDS.setOnBoardingState()
    }

    override suspend fun getOnBoardingState(): Boolean {
        return onBoardingLocalDS.getOnBoardingState()
    }

}