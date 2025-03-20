package com.mahmoud.altasherat.features.authentication.signup.domain.repository.local

import com.mahmoud.altasherat.features.signup.data.models.entity.SignUpEntity

internal interface ISignupLocalDS {
    suspend fun saveSignup(signupEntity: SignUpEntity)
}