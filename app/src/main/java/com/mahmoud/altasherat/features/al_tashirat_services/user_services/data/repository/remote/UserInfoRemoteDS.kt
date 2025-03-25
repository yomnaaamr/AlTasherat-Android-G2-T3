package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.remote.IUserInfoRemoteDS
import com.mahmoud.altasherat.features.profile_info.data.models.dto.UpdateAccDto
import com.mahmoud.altasherat.features.profile_info.data.models.request.UpdateAccRequest

class UserInfoRemoteDS(
    private val restApiNetworkProvider: IRestApiNetworkProvider
) : IUserInfoRemoteDS {
    override suspend fun updateRemoteUserInfo(updateRequest: UpdateAccRequest): UpdateAccDto {
        return restApiNetworkProvider.post(

        )
    }

}
