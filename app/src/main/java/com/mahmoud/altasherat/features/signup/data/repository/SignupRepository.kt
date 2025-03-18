package com.mahmoud.altasherat.features.signup.data.repository

import com.mahmoud.altasherat.features.signup.data.mappers.SignUpResponseMapper
import com.mahmoud.altasherat.features.signup.data.models.request.SignUpRequest
import com.mahmoud.altasherat.features.signup.domain.models.SignUp
import com.mahmoud.altasherat.features.signup.domain.repository.ISignupRepository
import com.mahmoud.altasherat.features.signup.domain.repository.remote.ISignupRemoteDS

internal class SignupRepository(
    private val remoteDS: ISignupRemoteDS
): ISignupRepository {
    override suspend fun signup(signupRequest: SignUpRequest): SignUp {
        val response = remoteDS.signup(signupRequest)
        return SignUpResponseMapper.dtoToDomain(response)
    }

    override suspend fun saveSignup(signup: SignUp) {
        TODO("Not yet implemented")
    }
}