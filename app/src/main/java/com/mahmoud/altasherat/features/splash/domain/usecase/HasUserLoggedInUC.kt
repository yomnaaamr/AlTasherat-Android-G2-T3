package com.mahmoud.altasherat.features.splash.domain.usecase

import com.mahmoud.altasherat.features.splash.domain.repository.ISplashRepository

class HasUserLoggedInUC(
    private val repository: ISplashRepository
) {
    suspend operator fun invoke(): Boolean{
        return repository.getUserAccessToken().isNotEmpty()
    }
}