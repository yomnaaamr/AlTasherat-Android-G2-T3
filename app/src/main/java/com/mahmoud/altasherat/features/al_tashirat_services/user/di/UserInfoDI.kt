package com.mahmoud.altasherat.features.al_tashirat_services.user.di

import com.google.gson.Gson
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.repository.UserInfoRepository
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.repository.local.UserInfoLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository.IUserInfoRepository
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.usecase.GetUserAccessToken
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.usecase.GetUserInfoUC
import com.mahmoud.altasherat.features.update_account.domain.repository.remote.IUpdateAccRemoteDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object UserInfoDI {

    @Provides
    fun provideGetUserInfoUC(
        userRepository: IUserInfoRepository
    ): GetUserInfoUC {
        return GetUserInfoUC(userRepository)
    }

//    @Provides
//    fun provideUserRemoteDS(
//        restApiNetworkProvider: IRestApiNetworkProvider
//    ): {
//        return UpdateAccRemoteDS(restApiNetworkProvider)
//    }

    @Provides
    fun provideUserLocalDS(
        localStorageProvider: ILocalStorageProvider,
        gson: Gson
    ): IUserInfoLocalDS {
        return UserInfoLocalDS(localStorageProvider, gson)
    }

    @Provides
    fun provideUserRepository(
        localDataSource: IUserInfoLocalDS,
        remoteDataSource: IUpdateAccRemoteDS
    ): IUserInfoRepository {
        return UserInfoRepository(remoteDataSource, localDataSource)
    }

    @Provides
    fun provideGetUserTokenUC(repository: IUserInfoRepository): GetUserAccessToken {
        return GetUserAccessToken(repository)
    }

}