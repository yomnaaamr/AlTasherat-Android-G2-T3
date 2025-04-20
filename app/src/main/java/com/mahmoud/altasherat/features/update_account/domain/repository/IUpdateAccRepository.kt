package com.mahmoud.altasherat.features.update_account.domain.repository

import com.mahmoud.altasherat.features.update_account.data.models.request.UpdateAccRequest
import com.mahmoud.altasherat.features.update_account.domain.models.UpdateAcc

interface IUpdateAccRepository {
    suspend fun updateLocalUserInfo(updateAcc: UpdateAcc)
    suspend fun updateRemoteUserInfo(updateRequest: UpdateAccRequest): UpdateAcc
}