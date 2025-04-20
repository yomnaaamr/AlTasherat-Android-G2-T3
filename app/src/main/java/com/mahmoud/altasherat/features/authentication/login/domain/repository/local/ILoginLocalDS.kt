package com.mahmoud.altasherat.features.authentication.login.domain.repository.local

import com.mahmoud.altasherat.features.authentication.login.data.models.entity.LoginEntity


interface ILoginLocalDS {
    suspend fun saveLogin(loginEntity: LoginEntity)
}