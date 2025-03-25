package com.mahmoud.altasherat.features.al_tashirat_services.user_services.di

import com.google.gson.Gson
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.repository.UserInfoRepository
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.repository.local.UserInfoLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.repository.remote.UserInfoRemoteDS
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.IUserInfoRepository
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.remote.IUserInfoRemoteDS
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.usecase.GetUserInfoUC
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.usecase.UpdateUserInfoUC
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

    @Provides
    fun provideUserRemoteDS(
        restApiNetworkProvider: IRestApiNetworkProvider
    ): IUserInfoRemoteDS {
        return UserInfoRemoteDS(restApiNetworkProvider)
    }

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
        remoteDataSource: IUserInfoRemoteDS
    ): IUserInfoRepository {
        return UserInfoRepository(remoteDataSource, localDataSource)
    }

    @Provides
    fun provideUpdateUserInfoUC(repository: IUserInfoRepository): UpdateUserInfoUC {
        return UpdateUserInfoUC(repository)
    }

}