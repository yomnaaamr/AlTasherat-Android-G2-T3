package com.mahmoud.altasherat.features.delete_account.delete_acc.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.common.util.Constants.AUTHORIZATION
import com.mahmoud.altasherat.common.util.Constants.CONTENT_TYPE_JSON
import com.mahmoud.altasherat.common.util.Constants.DELETE_ACCOUNT_ENDPOINT
import com.mahmoud.altasherat.common.util.Constants.HEADER_ACCEPT
import com.mahmoud.altasherat.features.delete_account.delete_acc.data.models.request.DeleteAccRequest
import com.mahmoud.altasherat.features.delete_account.delete_acc.domain.repository.remote.IDeleteAccountRemoteDS

class DeleteAccountRemoteDS(
    private val restApiNetworkProvider: IRestApiNetworkProvider
) : IDeleteAccountRemoteDS {
    override suspend fun deleteAccount(passwordRequest: DeleteAccRequest, token: String): String {
        return restApiNetworkProvider.post(
            endpoint = DELETE_ACCOUNT_ENDPOINT,
            body = passwordRequest,
            headers = mapOf(
                HEADER_ACCEPT to CONTENT_TYPE_JSON,
                AUTHORIZATION to "Bearer $token"
            ),
            responseType = String::class,
        )
    }
}