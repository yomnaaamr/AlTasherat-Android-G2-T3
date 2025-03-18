package com.mahmoud.altasherat.features.login.domain.repository

import com.mahmoud.altasherat.features.login.data.models.entity.LoginEntity
import com.mahmoud.altasherat.features.login.data.models.request.LoginRequest
import com.mahmoud.altasherat.features.login.domain.models.Login

interface ILoginRepository {
    suspend fun phoneLogin(loginRequest: LoginRequest): Login

    suspend fun getLogin():LoginEntity
    suspend fun saveLogin(login: Login)

}