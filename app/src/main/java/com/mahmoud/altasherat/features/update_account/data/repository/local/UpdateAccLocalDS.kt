package com.mahmoud.altasherat.features.update_account.data.repository.local

import com.google.gson.Gson
import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.update_account.data.models.entity.UpdateAccEntity
import com.mahmoud.altasherat.features.update_account.domain.repository.local.IUpdateAccLocalDS

class UpdateAccLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
    private val gson: Gson
) : IUpdateAccLocalDS {

    override suspend fun updateLocalUserInfo(updateAccEntity: UpdateAccEntity) {
        val userJson = gson.toJson(updateAccEntity.user)
        localStorageProvider.update(StorageKeyEnum.USER, userJson, String::class)
    }

}