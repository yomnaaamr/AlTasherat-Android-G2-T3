package com.mahmoud.altasherat.onBoarding.domain.useCase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.onBoarding.domain.repository.IOnBoardingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetOnBoardingVisibilityUC @Inject constructor(private val repository: IOnBoardingRepository) {
    suspend operator fun invoke(): Boolean {
        return repository.getOnBoardingVisibility()
    }
}
