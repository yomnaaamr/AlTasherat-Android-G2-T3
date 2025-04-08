package com.mahmoud.altasherat.features.al_tashirat_services.user.data.repository

import com.mahmoud.altasherat.features.al_tashirat_services.user.data.mappers.UserMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.User
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository.IUserInfoRepository
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.update_account.domain.repository.remote.IUpdateAccRemoteDS

class UserInfoRepository(
    private val userRemoteDS: IUpdateAccRemoteDS,
    private val userLocalDS: IUserInfoLocalDS
) : IUserInfoRepository {
    override suspend fun getUserInfo(): User {
        val userEntity = userLocalDS.getUserInfo()
        return UserMapper.entityToDomain(userEntity)
    }

    override suspend fun getUserAccessToken(): String {
        return userLocalDS.getUserAccessToken()
    }

    override suspend fun deleteUserAccessToken() {
        userLocalDS.deleteUserAccessToken()
    }

    override suspend fun deleteUserInfo() {
        userLocalDS.deleteUserInfo()
    }

}