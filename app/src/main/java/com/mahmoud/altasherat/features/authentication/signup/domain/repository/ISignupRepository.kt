package com.mahmoud.altasherat.features.authentication.signup.domain.repository

import com.mahmoud.altasherat.features.authentication.signup.data.models.request.SignUpRequest
import com.mahmoud.altasherat.features.authentication.signup.domain.models.SignUp

interface ISignupRepository {
    suspend fun signup(signupRequest: SignUpRequest): SignUp
    suspend fun saveSignup(signup: SignUp)
}