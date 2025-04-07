package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.repository.local

import com.google.gson.Gson
import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.entity.UserEntity
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.local.IUserInfoLocalDS

class UserInfoLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
    private val gson: Gson
) : IUserInfoLocalDS {
    override suspend fun getUserInfo(): UserEntity {
        val userEntityString = localStorageProvider.get(StorageKeyEnum.USER, "", String::class)
        val userEntity = gson.fromJson(userEntityString, UserEntity::class.java)
        return userEntity
    }

    override suspend fun getUserAccessToken(): String {
        return localStorageProvider.get(StorageKeyEnum.ACCESS_TOKEN, "", String::class)
    }

    override suspend fun deleteUserAccessToken() {
        localStorageProvider.delete(StorageKeyEnum.ACCESS_TOKEN, String::class)
    }

    override suspend fun deleteUserInfo() {
        localStorageProvider.delete(StorageKeyEnum.USER, String::class)
    }
}