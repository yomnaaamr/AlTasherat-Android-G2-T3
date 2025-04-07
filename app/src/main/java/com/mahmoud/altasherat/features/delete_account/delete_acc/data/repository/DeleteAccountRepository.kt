package com.mahmoud.altasherat.features.delete_account.delete_acc.data.repository

import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.delete_account.delete_acc.data.mappers.DeleteAccMapper
import com.mahmoud.altasherat.features.delete_account.delete_acc.data.models.request.DeleteAccRequest
import com.mahmoud.altasherat.features.delete_account.delete_acc.domain.models.DeleteAcc
import com.mahmoud.altasherat.features.delete_account.delete_acc.domain.repository.IDeleteAccountRepository
import com.mahmoud.altasherat.features.delete_account.delete_acc.domain.repository.remote.IDeleteAccountRemoteDS

class DeleteAccountRepository(
    private val deleteAccDS: IDeleteAccountRemoteDS,
    private val userLocalDS: IUserInfoLocalDS

) : IDeleteAccountRepository {
    override suspend fun deleteAccount(passwordRequest: DeleteAccRequest): DeleteAcc {
        return DeleteAccMapper.dtoToDomain(
            deleteAccDS.deleteAccount(
                passwordRequest,
                userLocalDS.getUserAccessToken()
            )
        )
    }
}