package com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository

import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.User

interface IUserInfoRepository {
    suspend fun getUserInfo(): User
    suspend fun getUserAccessToken(): String
}