package com.mahmoud.altasherat.features.authentication.signup.data.repository

import com.mahmoud.altasherat.features.authentication.signup.data.mappers.SignUpResponseMapper
import com.mahmoud.altasherat.features.authentication.signup.data.models.request.SignUpRequest
import com.mahmoud.altasherat.features.authentication.signup.domain.models.SignUp
import com.mahmoud.altasherat.features.authentication.signup.domain.repository.ISignupRepository
import com.mahmoud.altasherat.features.authentication.signup.domain.repository.local.ISignupLocalDS
import com.mahmoud.altasherat.features.authentication.signup.domain.repository.remote.ISignupRemoteDS

internal class SignupRepository(
    private val remoteDS: ISignupRemoteDS,
    private val localDS: ISignupLocalDS
): ISignupRepository {

    override suspend fun signup(signupRequest: SignUpRequest): SignUp {
        val response = remoteDS.signup(signupRequest)
        return SignUpResponseMapper.dtoToDomain(response)
    }

    override suspend fun saveSignup(signup: SignUp) {
        val signupEntity = SignUpResponseMapper.domainToEntity(signup)
        localDS.saveSignup(signupEntity)
    }
}