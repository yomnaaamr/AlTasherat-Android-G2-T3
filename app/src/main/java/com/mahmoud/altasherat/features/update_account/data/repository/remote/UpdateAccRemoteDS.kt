package com.mahmoud.altasherat.features.update_account.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.common.util.Constants.AUTHORIZATION
import com.mahmoud.altasherat.common.util.Constants.CONTENT_TYPE_JSON
import com.mahmoud.altasherat.common.util.Constants.HEADER_ACCEPT
import com.mahmoud.altasherat.common.util.Constants.UPDATE_ACCOUNT_ENDPOINT
import com.mahmoud.altasherat.features.al_tashirat_services.user.util.toImagePart
import com.mahmoud.altasherat.features.update_account.data.models.dto.UpdateAccDto
import com.mahmoud.altasherat.features.update_account.data.models.request.UpdateAccRequest
import com.mahmoud.altasherat.features.update_account.domain.repository.remote.IUpdateAccRemoteDS

class UpdateAccRemoteDS(
    private val restApiNetworkProvider: IRestApiNetworkProvider
) : IUpdateAccRemoteDS {
    override suspend fun updateRemoteUserInfo(
        updateRequest: UpdateAccRequest, token: String
    ): UpdateAccDto {
        val requestMap = updateRequest.createPartMap()

        return restApiNetworkProvider.updateAccount(
            endpoint = UPDATE_ACCOUNT_ENDPOINT,
            image = updateRequest.image?.toImagePart(),
            data = requestMap,
            headers = mapOf(
                HEADER_ACCEPT to CONTENT_TYPE_JSON,
//                HEADER_CONTENT_TYPE to CONTENT_TYPE_MULTIPART,
                AUTHORIZATION to "Bearer $token"
            ),
            responseType = UpdateAccDto::class
        )
    }

}
