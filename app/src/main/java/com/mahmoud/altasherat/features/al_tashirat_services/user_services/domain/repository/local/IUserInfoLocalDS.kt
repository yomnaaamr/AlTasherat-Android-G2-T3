package com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.local

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.entity.UserEntity

interface IUserInfoLocalDS {
    suspend fun getUserInfo(): UserEntity
    suspend fun getUserAccessToken(): String
    suspend fun deleteUserAccessToken()
    suspend fun deleteUserInfo()
}