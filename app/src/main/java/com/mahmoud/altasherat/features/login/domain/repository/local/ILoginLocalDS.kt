package com.mahmoud.altasherat.features.login.domain.repository.local

import com.mahmoud.altasherat.features.login.data.models.entity.LoginEntity
import com.mahmoud.altasherat.features.login.data.models.entity.UserEntity

interface ILoginLocalDS {
    suspend fun saveLogin(loginEntity:LoginEntity)
    suspend fun getLogin():LoginEntity
}