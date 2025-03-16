package com.mahmoud.altasherat.features.onBoarding.domain.useCase

import com.mahmoud.altasherat.features.onBoarding.domain.repository.IOnBoardingRepository
import javax.inject.Inject

class IsFirstTimeToLaunchTheAppUC @Inject constructor(private val repository: IOnBoardingRepository) {
    suspend operator fun invoke(): Boolean {
        return repository.isFirstTimeToLaunchTheApp()
    }
}
