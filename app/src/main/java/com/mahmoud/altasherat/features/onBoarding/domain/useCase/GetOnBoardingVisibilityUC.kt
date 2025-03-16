package com.mahmoud.altasherat.features.onBoarding.domain.useCase

import com.mahmoud.altasherat.features.onBoarding.domain.repository.IOnBoardingRepository
import javax.inject.Inject

class GetOnBoardingVisibilityUC @Inject constructor(private val repository: IOnBoardingRepository) {
    suspend operator fun invoke(): Boolean {
        return repository.getOnBoardingVisibility()
    }
}
