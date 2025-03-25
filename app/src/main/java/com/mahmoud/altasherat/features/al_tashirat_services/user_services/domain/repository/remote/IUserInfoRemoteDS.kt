package com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.remote

import com.mahmoud.altasherat.features.profile_info.data.models.dto.UpdateAccDto
import com.mahmoud.altasherat.features.profile_info.data.models.request.UpdateAccRequest

interface IUserInfoRemoteDS {
    suspend fun updateRemoteUserInfo(updateRequest: UpdateAccRequest): UpdateAccDto
}