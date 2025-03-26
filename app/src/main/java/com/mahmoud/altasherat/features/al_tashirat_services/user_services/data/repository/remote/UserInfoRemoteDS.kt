package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.repository.remote

import android.util.Log
import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.common.util.Constants.AUTHORIZATION
import com.mahmoud.altasherat.common.util.Constants.CONTENT_TYPE_JSON
import com.mahmoud.altasherat.common.util.Constants.HEADER_ACCEPT
import com.mahmoud.altasherat.common.util.Constants.HEADER_CONTENT_TYPE
import com.mahmoud.altasherat.common.util.Constants.UPDATE_ACCOUNT_ENDPOINT
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.remote.IUserInfoRemoteDS
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.util.createPartMap
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.util.mapToUpdateAccRequest
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.util.toImagePart
import com.mahmoud.altasherat.features.profile_info.data.models.dto.UpdateAccDto
import com.mahmoud.altasherat.features.profile_info.data.models.request.UpdateAccRequest

class UserInfoRemoteDS(
    private val restApiNetworkProvider: IRestApiNetworkProvider
) : IUserInfoRemoteDS {
    override suspend fun updateRemoteUserInfo(
        updateRequest: UpdateAccRequest, token: String
    ): UpdateAccDto {
        Log.d("REQUEST_DS", updateRequest.toString())
        val requestMap = createPartMap(updateRequest)
        Log.d("REQUEST_MODEL", mapToUpdateAccRequest(requestMap).toString())
        Log.d(
            "RESPONSE_DTO", restApiNetworkProvider.updateAccount(
                endpoint = UPDATE_ACCOUNT_ENDPOINT,
                image = updateRequest.image?.toImagePart(),
                data = requestMap,
                headers = mapOf(
                    HEADER_ACCEPT to CONTENT_TYPE_JSON,
                    HEADER_CONTENT_TYPE to CONTENT_TYPE_JSON,
                    AUTHORIZATION to "Bearer $token"
                ),
                responseType = UpdateAccDto::class
            ).toString()
        )
        return restApiNetworkProvider.updateAccount(
            endpoint = UPDATE_ACCOUNT_ENDPOINT,
            image = updateRequest.image?.toImagePart(),
            data = requestMap,
            headers = mapOf(
                HEADER_ACCEPT to CONTENT_TYPE_JSON,
                HEADER_CONTENT_TYPE to CONTENT_TYPE_JSON,
                AUTHORIZATION to "Bearer $token"
            ),
            responseType = UpdateAccDto::class
        )
    }

}
