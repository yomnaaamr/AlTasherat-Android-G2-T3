package com.mahmoud.altasherat.features.onBoarding.data.repository.local

import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.onBoarding.domain.repository.local.IOnBoardingLocalDS

class OnBoardingLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
) : IOnBoardingLocalDS {

    override suspend fun setOnBoardingShown() {
        localStorageProvider.save(StorageKeyEnum.ONBOARDING, false, Boolean::class)
    }

    override suspend fun isFirstTimeToLaunchTheApp(): Boolean {
        return localStorageProvider.get(StorageKeyEnum.ONBOARDING, true, Boolean::class)
    }
}