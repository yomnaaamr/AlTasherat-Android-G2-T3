package com.mahmoud.altasherat.features.delete_account.delete_acc.domain.repository.remote

import com.mahmoud.altasherat.features.delete_account.delete_acc.data.models.dto.DeleteAccDto
import com.mahmoud.altasherat.features.delete_account.delete_acc.data.models.request.DeleteAccRequest

interface IDeleteAccountRemoteDS {
    suspend fun deleteAccount(passwordRequest: DeleteAccRequest): DeleteAccDto
}