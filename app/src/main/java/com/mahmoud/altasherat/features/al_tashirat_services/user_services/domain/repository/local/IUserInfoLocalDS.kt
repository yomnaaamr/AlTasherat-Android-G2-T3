package com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.local

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.entity.UserEntity
import com.mahmoud.altasherat.features.profile_info.data.models.entity.UpdateAccEntity

interface IUserInfoLocalDS {
    suspend fun getUserInfo(): UserEntity
    suspend fun updateLocalUserInfo(updateAccEntity: UpdateAccEntity)
}