package com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User
import com.mahmoud.altasherat.features.profile_info.data.models.request.UpdateAccRequest
import com.mahmoud.altasherat.features.profile_info.domain.models.UpdateAcc

interface IUserInfoRepository {
    suspend fun getUserInfo(): User
    suspend fun updateLocalUserInfo(updateAcc: UpdateAcc)
    suspend fun updateRemoteUserInfo(updateRequest: UpdateAccRequest): UpdateAcc
}