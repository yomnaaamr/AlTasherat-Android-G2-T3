package com.mahmoud.altasherat.features.update_account.data.repository

import android.util.Log
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.update_account.data.mappers.UpdateAccMapper
import com.mahmoud.altasherat.features.update_account.data.models.request.UpdateAccRequest
import com.mahmoud.altasherat.features.update_account.domain.models.UpdateAcc
import com.mahmoud.altasherat.features.update_account.domain.repository.IUpdateAccRepository
import com.mahmoud.altasherat.features.update_account.domain.repository.local.IUpdateAccLocalDS
import com.mahmoud.altasherat.features.update_account.domain.repository.remote.IUpdateAccRemoteDS

class UpdateAccRepository(
    private val updateRemoteDS: IUpdateAccRemoteDS,
    private val updateLocalDS: IUpdateAccLocalDS,
    private val userLocalDS: IUserInfoLocalDS,
) : IUpdateAccRepository {


    override suspend fun updateLocalUserInfo(updateAcc: UpdateAcc) {
        val updateAccEntity = UpdateAccMapper.domainToEntity(updateAcc)
        updateLocalDS.updateLocalUserInfo(updateAccEntity)
    }

    override suspend fun updateRemoteUserInfo(updateRequest: UpdateAccRequest): UpdateAcc {
        Log.d("REQUEST_DOMAIN", updateRequest.toString())
        val response =
            updateRemoteDS.updateRemoteUserInfo(updateRequest, userLocalDS.getUserAccessToken())
        Log.d("RESPONSE_DOMAIN", UpdateAccMapper.dtoToDomain(response).toString())
        return UpdateAccMapper.dtoToDomain(response)
    }


}