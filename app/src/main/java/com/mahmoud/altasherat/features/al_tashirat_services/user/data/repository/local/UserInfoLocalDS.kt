package com.mahmoud.altasherat.features.al_tashirat_services.user.data.repository.local

import android.util.Log
import com.google.gson.Gson
import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.entity.UserEntity
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository.local.IUserInfoLocalDS

class UserInfoLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
    private val gson: Gson
) : IUserInfoLocalDS {
    override suspend fun getUserInfo(): UserEntity {
        val userEntityString = localStorageProvider.get(StorageKeyEnum.USER, "", String::class)
        Log.d("USER_STRING_DS", userEntityString)
        val userEntity = gson.fromJson(userEntityString, UserEntity::class.java)
        Log.d("USER_ENTITY_DS", userEntity.toString())
        return userEntity
    }

    override suspend fun getUserAccessToken(): String {
        return localStorageProvider.get(StorageKeyEnum.ACCESS_TOKEN, "", String::class)
    }
}