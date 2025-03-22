package com.mahmoud.altasherat.features.authentication.login.data.repository.local

import com.google.gson.Gson
import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.authentication.login.data.models.entity.LoginEntity
import com.mahmoud.altasherat.features.authentication.login.domain.repository.local.ILoginLocalDS

class LoginLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
    private val gson: Gson
) : ILoginLocalDS {
    override suspend fun saveLogin(loginEntity: LoginEntity) {
        localStorageProvider.save(
            StorageKeyEnum.ACCESS_TOKEN,
            loginEntity.token,
            String::class
        )
        val userJson = gson.toJson(loginEntity.user)
        localStorageProvider.save(StorageKeyEnum.USER, userJson, String::class)
    }
}