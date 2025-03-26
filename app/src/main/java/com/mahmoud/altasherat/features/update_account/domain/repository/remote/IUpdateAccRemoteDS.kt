package com.mahmoud.altasherat.features.update_account.domain.repository.remote

import com.mahmoud.altasherat.features.update_account.data.models.dto.UpdateAccDto
import com.mahmoud.altasherat.features.update_account.data.models.request.UpdateAccRequest

interface IUpdateAccRemoteDS {
    suspend fun updateRemoteUserInfo(updateRequest: UpdateAccRequest, token: String): UpdateAccDto
}