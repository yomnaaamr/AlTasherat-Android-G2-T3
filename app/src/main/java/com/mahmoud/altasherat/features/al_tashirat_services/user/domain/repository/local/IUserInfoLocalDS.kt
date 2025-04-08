package com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository.local

import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.entity.UserEntity

interface IUserInfoLocalDS {
    suspend fun getUserInfo(): UserEntity
    suspend fun getUserAccessToken(): String
    suspend fun deleteUserAccessToken()
    suspend fun deleteUserInfo()
}