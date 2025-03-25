package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.common.util.Constants.CONTENT_TYPE_JSON
import com.mahmoud.altasherat.common.util.Constants.HEADER_ACCEPT
import com.mahmoud.altasherat.common.util.Constants.HEADER_CONTENT_TYPE
import com.mahmoud.altasherat.common.util.Constants.UPDATE_ACCOUNT_ENDPOINT
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.remote.IUserInfoRemoteDS
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.util.createPartMap
import com.mahmoud.altasherat.features.profile_info.data.models.dto.UpdateAccDto
import com.mahmoud.altasherat.features.profile_info.data.models.request.UpdateAccRequest

class UserInfoRemoteDS(
    private val restApiNetworkProvider: IRestApiNetworkProvider
) : IUserInfoRemoteDS {
    override suspend fun updateRemoteUserInfo(updateRequest: UpdateAccRequest): UpdateAccDto {
        val requestMap = createPartMap(updateRequest)
        return restApiNetworkProvider.updateAccount(
            endpoint = UPDATE_ACCOUNT_ENDPOINT,
            data = requestMap,
            headers = mapOf(
                HEADER_ACCEPT to CONTENT_TYPE_JSON,
                HEADER_CONTENT_TYPE to CONTENT_TYPE_JSON
            ),
            responseType = UpdateAccDto::class
        )
    }

}
