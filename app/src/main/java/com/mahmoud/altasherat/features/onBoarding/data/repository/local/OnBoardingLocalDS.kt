package com.mahmoud.altasherat.features.onBoarding.data.repository.local

import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.onBoarding.domain.repository.local.IOnBoardingLocalDS

class OnBoardingLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
) : IOnBoardingLocalDS {

    override suspend fun setOnBoardingState() {
        localStorageProvider.save(StorageKeyEnum.ONBOARDING, true, Boolean::class)
    }

    override suspend fun getOnBoardingState(): Boolean {
        return localStorageProvider.get(StorageKeyEnum.ONBOARDING, false, Boolean::class)
    }
}