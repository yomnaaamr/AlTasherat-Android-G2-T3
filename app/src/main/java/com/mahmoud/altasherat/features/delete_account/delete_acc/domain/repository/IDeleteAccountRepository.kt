package com.mahmoud.altasherat.features.delete_account.delete_acc.domain.repository

import com.mahmoud.altasherat.features.delete_account.delete_acc.data.models.request.DeleteAccRequest

interface IDeleteAccountRepository {
    suspend fun deleteAccount(passwordRequest: DeleteAccRequest): String
}