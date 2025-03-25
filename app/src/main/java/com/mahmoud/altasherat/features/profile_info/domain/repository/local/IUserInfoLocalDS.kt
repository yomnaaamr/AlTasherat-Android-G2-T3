package com.mahmoud.altasherat.features.profile_info.domain.repository.local

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.entity.UserEntity

interface IUserInfoLocalDS {
    suspend fun getUserInfo(): UserEntity
}