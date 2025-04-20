package com.mahmoud.altasherat.features.update_account.data.repository

import com.mahmoud.altasherat.features.update_account.data.mappers.UpdateAccMapper
import com.mahmoud.altasherat.features.update_account.data.models.request.UpdateAccRequest
import com.mahmoud.altasherat.features.update_account.domain.models.UpdateAcc
import com.mahmoud.altasherat.features.update_account.domain.repository.IUpdateAccRepository
import com.mahmoud.altasherat.features.update_account.domain.repository.local.IUpdateAccLocalDS
import com.mahmoud.altasherat.features.update_account.domain.repository.remote.IUpdateAccRemoteDS

class UpdateAccRepository(
    private val updateRemoteDS: IUpdateAccRemoteDS,
    private val updateLocalDS: IUpdateAccLocalDS,
) : IUpdateAccRepository {


    override suspend fun updateLocalUserInfo(updateAcc: UpdateAcc) {
        val updateAccEntity = UpdateAccMapper.domainToEntity(updateAcc)
        updateLocalDS.updateLocalUserInfo(updateAccEntity)
    }

    override suspend fun updateRemoteUserInfo(updateRequest: UpdateAccRequest): UpdateAcc {
        val response =
            updateRemoteDS.updateRemoteUserInfo(updateRequest)
        return UpdateAccMapper.dtoToDomain(response)
    }


}