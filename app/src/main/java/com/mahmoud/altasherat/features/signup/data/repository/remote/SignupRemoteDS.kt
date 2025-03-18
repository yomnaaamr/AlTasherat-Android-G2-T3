package com.mahmoud.altasherat.features.signup.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.signup.data.models.dto.SignUpResponseDto
import com.mahmoud.altasherat.features.signup.data.models.request.SignUpRequest
import com.mahmoud.altasherat.features.signup.domain.repository.remote.ISignupRemoteDS

internal class SignupRemoteDS(
    private val restApiNetworkProvider: IRestApiNetworkProvider
) : ISignupRemoteDS {

    override suspend fun signup(signupRequest: SignUpRequest): SignUpResponseDto {
        return restApiNetworkProvider.post(
            endpoint = SIGNUP_ENDPOINT,
            body = signupRequest,
            headers = mapOf(
                HEADER_ACCEPT to CONTENT_TYPE_JSON,
                HEADER_CONTENT_TYPE to CONTENT_TYPE_JSON
            ),
            responseType = SignUpResponseDto::class
        )
    }


    companion object {
        private const val SIGNUP_ENDPOINT = "signup"
        private const val HEADER_ACCEPT = "Accept"
        private const val HEADER_CONTENT_TYPE = "Content-Type"
        private const val CONTENT_TYPE_JSON = "application/json"
    }
}