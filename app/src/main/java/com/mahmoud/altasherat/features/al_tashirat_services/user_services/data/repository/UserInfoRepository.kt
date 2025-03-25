package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.repository

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.mappers.UserMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User
import com.mahmoud.altasherat.features.profile_info.domain.repository.IUserInfoRepository
import com.mahmoud.altasherat.features.profile_info.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.profile_info.domain.repository.remote.IUserInfoRemoteDS

class UserInfoRepository(
    private val userRemoteDS: IUserInfoRemoteDS,
    private val userLocalDS: IUserInfoLocalDS
) : IUserInfoRepository {
    override suspend fun getUserInfo(): User {
        val userEntity = userLocalDS.getUserInfo()
        return UserMapper.entityToDomain(userEntity)
    }

}