package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.repository.local

import com.google.gson.Gson
import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.entity.UserEntity
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.profile_info.data.models.entity.UpdateAccEntity

class UserInfoLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
    private val gson: Gson
) : IUserInfoLocalDS {
    override suspend fun getUserInfo(): UserEntity {
        val userEntity = localStorageProvider.get(StorageKeyEnum.USER, "", String::class)
        return gson.fromJson(userEntity, UserEntity::class.java)
    }

    override suspend fun updateLocalUserInfo(updateAccEntity: UpdateAccEntity) {
        val userJson = gson.toJson(updateAccEntity.user)
        localStorageProvider.update(StorageKeyEnum.USER, userJson, String::class)
    }
}