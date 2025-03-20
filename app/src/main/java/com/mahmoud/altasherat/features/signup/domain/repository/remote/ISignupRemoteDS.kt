package com.mahmoud.altasherat.features.signup.domain.repository.remote

import com.mahmoud.altasherat.features.signup.data.models.dto.SignUpResponseDto
import com.mahmoud.altasherat.features.signup.data.models.request.SignUpRequest

internal interface ISignupRemoteDS {
    suspend fun signup(signupRequest: SignUpRequest): SignUpResponseDto
}