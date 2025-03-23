package com.mahmoud.altasherat.features.authentication.signup.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.common.util.Constants.CONTENT_TYPE_JSON
import com.mahmoud.altasherat.common.util.Constants.HEADER_ACCEPT
import com.mahmoud.altasherat.common.util.Constants.HEADER_CONTENT_TYPE
import com.mahmoud.altasherat.common.util.Constants.SIGNUP_ENDPOINT
import com.mahmoud.altasherat.features.authentication.signup.data.models.dto.SignUpResponseDto
import com.mahmoud.altasherat.features.authentication.signup.data.models.request.SignUpRequest
import com.mahmoud.altasherat.features.authentication.signup.domain.repository.remote.ISignupRemoteDS


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
}