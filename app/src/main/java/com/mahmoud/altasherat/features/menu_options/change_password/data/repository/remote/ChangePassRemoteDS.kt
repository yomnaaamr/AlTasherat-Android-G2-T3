package com.mahmoud.altasherat.features.menu_options.change_password.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.common.util.Constants.CHANGE_PASSWORD_ENDPOINT
import com.mahmoud.altasherat.common.util.Constants.CONTENT_TYPE_JSON
import com.mahmoud.altasherat.common.util.Constants.HEADER_ACCEPT
import com.mahmoud.altasherat.features.menu_options.change_password.data.models.dto.ChangePasswordDto
import com.mahmoud.altasherat.features.menu_options.change_password.data.models.request.ChangePassRequest
import com.mahmoud.altasherat.features.menu_options.change_password.domain.repository.remote.IChangePassRemoteDS

class ChangePassRemoteDS(
    private val networkProvider: IRestApiNetworkProvider
) : IChangePassRemoteDS {
    override suspend fun changePassword(request: ChangePassRequest): ChangePasswordDto {
        return networkProvider.post(
            endpoint = CHANGE_PASSWORD_ENDPOINT,
            body = request,
            headers = mapOf(
                HEADER_ACCEPT to CONTENT_TYPE_JSON
            ),
            responseType = ChangePasswordDto::class
        )
    }
}