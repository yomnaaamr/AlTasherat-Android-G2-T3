package com.mahmoud.altasherat.features.authentication.login.domain.repository

import com.mahmoud.altasherat.features.authentication.login.data.models.entity.LoginEntity
import com.mahmoud.altasherat.features.authentication.login.data.models.request.LoginRequest
import com.mahmoud.altasherat.features.authentication.login.domain.models.Login


interface ILoginRepository {
    suspend fun login(loginRequest: LoginRequest): Login
    suspend fun saveLogin(login: Login)

}