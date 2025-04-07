package com.mahmoud.altasherat.features.delete_account.delete_acc.domain.repository

import com.mahmoud.altasherat.features.delete_account.delete_acc.data.models.request.DeleteAccRequest
import com.mahmoud.altasherat.features.delete_account.delete_acc.domain.models.DeleteAcc

interface IDeleteAccountRepository {
    suspend fun deleteAccount(passwordRequest: DeleteAccRequest): DeleteAcc
}