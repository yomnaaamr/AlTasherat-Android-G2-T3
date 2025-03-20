package com.mahmoud.altasherat.features.login.data.repository

import android.util.Log
import com.mahmoud.altasherat.features.login.data.mappers.LoginMapper
import com.mahmoud.altasherat.features.login.data.models.entity.LoginEntity
import com.mahmoud.altasherat.features.login.data.models.request.LoginRequest
import com.mahmoud.altasherat.features.login.domain.models.Login
import com.mahmoud.altasherat.features.login.domain.repository.remote.ILoginRemoteDS
import com.mahmoud.altasherat.features.login.domain.repository.ILoginRepository
import com.mahmoud.altasherat.features.login.domain.repository.local.ILoginLocalDS

class LoginRepositoryImpl(
    private val loginRemoteDS: ILoginRemoteDS,
    private val loginLocalDS: ILoginLocalDS,
) : ILoginRepository {
    override suspend fun phoneLogin(loginRequest: LoginRequest): Login {
        Log.d("AITASHERAT", "phoneLogin  = $loginRequest ")
        val loginResponse = loginRemoteDS.phoneLogin(loginRequest)
        Log.d("AITASHERAT", "LoginRepo response = $loginResponse ")
        return LoginMapper.dtoToDomain(loginResponse)
    }

    override suspend fun getLogin(): LoginEntity {
        return loginLocalDS.getLogin()
    }

    override suspend fun saveLogin(login: Login) {
        loginLocalDS.saveLogin(LoginMapper.domainToEntity(login))
    }
}