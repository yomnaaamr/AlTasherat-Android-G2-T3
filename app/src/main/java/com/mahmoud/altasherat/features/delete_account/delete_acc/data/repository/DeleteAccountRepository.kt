package com.mahmoud.altasherat.features.delete_account.delete_acc.data.repository

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.delete_account.delete_acc.domain.repository.IDeleteAccountRepository
import com.mahmoud.altasherat.features.delete_account.delete_acc.domain.repository.remote.IDeleteAccountRemoteDS

class DeleteAccountRepository(
    private val deleteAccDS: IDeleteAccountRemoteDS,
    private val userLocalDS: IUserInfoLocalDS

) : IDeleteAccountRepository {
    override suspend fun deleteAccount(password: String): String {
        return deleteAccDS.deleteAccount(password, userLocalDS.getUserAccessToken())
    }
}