package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.repository

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.mappers.UserMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.IUserInfoRepository
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.local.IUserInfoLocalDS
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

}