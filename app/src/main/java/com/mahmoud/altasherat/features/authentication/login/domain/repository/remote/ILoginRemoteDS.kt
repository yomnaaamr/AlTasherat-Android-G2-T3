package com.mahmoud.altasherat.features.authentication.login.domain.repository.remote

import com.mahmoud.altasherat.features.authentication.login.data.models.dto.LoginResponseDto
import com.mahmoud.altasherat.features.authentication.login.data.models.request.LoginRequest


interface ILoginRemoteDS {
    suspend fun login(
        loginRequest: LoginRequest,
    ): LoginResponseDto
}