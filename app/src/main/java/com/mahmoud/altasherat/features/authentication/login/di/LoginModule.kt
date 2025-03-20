package com.mahmoud.altasherat.features.authentication.login.di

import com.mahmoud.altasherat.common.data.repository.local.LocalStorageProvider
import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.authentication.login.data.repository.LoginRepository
import com.mahmoud.altasherat.features.authentication.login.data.repository.local.LoginLocalDS
import com.mahmoud.altasherat.features.authentication.login.data.repository.remote.LoginRemoteDS
import com.mahmoud.altasherat.features.authentication.login.domain.repository.ILoginRepository
import com.mahmoud.altasherat.features.authentication.login.domain.repository.local.ILoginLocalDS
import com.mahmoud.altasherat.features.authentication.login.domain.repository.remote.ILoginRemoteDS
import com.mahmoud.altasherat.features.authentication.login.domain.useCases.LoginUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {
    @Provides
    fun provideLoginRemoteDS(
        apiNetworkProvider: IRestApiNetworkProvider
    ): ILoginRemoteDS {
        return LoginRemoteDS(
            apiNetworkProvider = apiNetworkProvider
        )
    }

    @Provides
    fun provideLoginLocalDS(localStorageProvider: LocalStorageProvider): ILoginLocalDS {
        return LoginLocalDS(localStorageProvider)
    }

    @Provides
    fun provideLoginRepository(
        loginLocalDS: ILoginLocalDS,
        loginRemoteDS: ILoginRemoteDS,
    ): ILoginRepository {
        return LoginRepository(loginLocalDS = loginLocalDS, loginRemoteDS = loginRemoteDS)
    }

    @Provides
    fun providePhoneLoginUseCase(repository: ILoginRepository): LoginUC {
        return LoginUC(repository)
    }
}