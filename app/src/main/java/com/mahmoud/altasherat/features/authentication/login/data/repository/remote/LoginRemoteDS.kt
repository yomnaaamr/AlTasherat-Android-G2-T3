package com.mahmoud.altasherat.features.authentication.login.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.authentication.login.data.models.dto.LoginResponseDto
import com.mahmoud.altasherat.features.authentication.login.data.models.request.LoginRequest
import com.mahmoud.altasherat.features.authentication.login.domain.repository.remote.ILoginRemoteDS

class LoginRemoteDS(
    private val apiNetworkProvider: IRestApiNetworkProvider
) : ILoginRemoteDS {
    override suspend fun login(
        loginRequest: LoginRequest,
    ): LoginResponseDto {
        return apiNetworkProvider.post(
            endpoint = LOGIN_ENDPOINT,
            body = loginRequest,
            headers = mapOf(HEADER_ACCEPT to CONTENT_TYPE_JSON),
            responseType = LoginResponseDto::class
        )
    }

    companion object {
        private const val LOGIN_ENDPOINT = "login"
        private const val HEADER_ACCEPT = "Accept"
        private const val CONTENT_TYPE_JSON = "application/json"

    }
}