package com.mahmoud.altasherat.features.onBoarding.domain.useCase

import com.mahmoud.altasherat.features.onBoarding.domain.repository.IOnBoardingRepository
import javax.inject.Inject

class SetOnBoardingAsShownUC @Inject constructor( private val repository: IOnBoardingRepository) {

    suspend operator fun invoke() {
        repository.saveOnBoardingShown()
    }

}
