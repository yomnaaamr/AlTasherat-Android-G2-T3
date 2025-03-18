package com.mahmoud.altasherat.features.login.data.repository.local

import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.data.util.getModelFromJSON
import com.mahmoud.altasherat.common.data.util.toJson
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.login.data.models.entity.LoginEntity
import com.mahmoud.altasherat.features.login.domain.repository.local.ILoginLocalDS

class LoginLocalDSImpl(private val localStorageProvider: ILocalStorageProvider) : ILoginLocalDS {
    override suspend fun saveLogin(loginEntity: LoginEntity) {
        val loginEntityAsGson = loginEntity.toJson()
        localStorageProvider.save(StorageKeyEnum.LOGIN, loginEntityAsGson, String::class)
    }

    override suspend fun getLogin(): LoginEntity {
        return localStorageProvider.get(StorageKeyEnum.LOGIN, "", String::class)
            .getModelFromJSON(LoginEntity::class.java)
    }
}