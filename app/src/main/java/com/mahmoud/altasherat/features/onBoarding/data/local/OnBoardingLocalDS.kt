package com.mahmoud.altasherat.features.onBoarding.data.local

import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.onBoarding.domain.local.IOnBoardingLocalDS

class OnBoardingLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
) : IOnBoardingLocalDS {

    override suspend fun setOnBoardingShown() {
        localStorageProvider.save(StorageKeyEnum.ONBOARDING, true, Boolean::class)
    }

    override suspend fun getOnBoardingVisibility(): Boolean {
        return localStorageProvider.get(StorageKeyEnum.ONBOARDING, false, Boolean::class)
    }
}