package com.mahmoud.altasherat.features.update_account.domain.repository.local

import com.mahmoud.altasherat.features.update_account.data.models.entity.UpdateAccEntity

interface IUpdateAccLocalDS {
    suspend fun updateLocalUserInfo(updateAccEntity: UpdateAccEntity)

}