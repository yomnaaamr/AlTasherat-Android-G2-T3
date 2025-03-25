package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.repository

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.mappers.UserMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.IUserInfoRepository
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.remote.IUserInfoRemoteDS
import com.mahmoud.altasherat.features.profile_info.data.mappers.UpdateAccMapper
import com.mahmoud.altasherat.features.profile_info.data.models.request.UpdateAccRequest
import com.mahmoud.altasherat.features.profile_info.domain.models.UpdateAcc

class UserInfoRepository(
    private val userRemoteDS: IUserInfoRemoteDS,
    private val userLocalDS: IUserInfoLocalDS
) : IUserInfoRepository {
    override suspend fun getUserInfo(): User {
        val userEntity = userLocalDS.getUserInfo()
        return UserMapper.entityToDomain(userEntity)
    }

    override suspend fun updateLocalUserInfo(updateAcc: UpdateAcc) {
        val updateAccEntity = UpdateAccMapper.domainToEntity(updateAcc)
        userLocalDS.updateLocalUserInfo(updateAccEntity)
    }

    override suspend fun updateRemoteUserInfo(updateRequest: UpdateAccRequest): UpdateAcc {
        val response = userRemoteDS.updateRemoteUserInfo(updateRequest)
        return UpdateAccMapper.dtoToDomain(response)
    }


}