package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.repository.remote

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.profile_info.domain.repository.remote.IUserInfoRemoteDS

class UserInfoRemoteDS(
    private val restApiNetworkProvider: IRestApiNetworkProvider
) : IUserInfoRemoteDS {

}
