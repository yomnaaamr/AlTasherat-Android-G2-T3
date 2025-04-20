package com.mahmoud.altasherat.features.authentication.login.data.repository

import com.mahmoud.altasherat.features.authentication.login.data.mappers.LoginMapper
import com.mahmoud.altasherat.features.authentication.login.data.models.request.LoginRequest
import com.mahmoud.altasherat.features.authentication.login.domain.models.Login
import com.mahmoud.altasherat.features.authentication.login.domain.repository.ILoginRepository
import com.mahmoud.altasherat.features.authentication.login.domain.repository.local.ILoginLocalDS
import com.mahmoud.altasherat.features.authentication.login.domain.repository.remote.ILoginRemoteDS

class LoginRepository(
    private val loginRemoteDS: ILoginRemoteDS,
    private val loginLocalDS: ILoginLocalDS,
) : ILoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Login {
        val loginResponse = loginRemoteDS.login(loginRequest)
        return LoginMapper.dtoToDomain(loginResponse)
    }

    override suspend fun saveLogin(login: Login) {
        loginLocalDS.saveLogin(LoginMapper.domainToEntity(login))
    }
}