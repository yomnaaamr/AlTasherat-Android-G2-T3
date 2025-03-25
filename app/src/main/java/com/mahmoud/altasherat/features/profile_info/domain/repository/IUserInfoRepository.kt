package com.mahmoud.altasherat.features.profile_info.domain.repository

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User

interface IUserInfoRepository {
    suspend fun getUserInfo(): User
}