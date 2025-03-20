package com.mahmoud.altasherat.features.authentication.login.di

import com.mahmoud.altasherat.common.data.repository.local.LocalStorageProvider
import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.login.data.repository.LoginRepositoryImpl
import com.mahmoud.altasherat.features.login.data.repository.local.LoginLocalDSImpl
import com.mahmoud.altasherat.features.login.data.repository.remote.LoginRemoteDSImpl
import com.mahmoud.altasherat.features.login.domain.repository.ILoginRepository
import com.mahmoud.altasherat.features.login.domain.repository.local.ILoginLocalDS
import com.mahmoud.altasherat.features.login.domain.repository.remote.ILoginRemoteDS
import com.mahmoud.altasherat.features.login.domain.useCases.PhoneLoginUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {
    @Provides
    fun provideLoginRemoteDS(apiNetworkProvider: IRestApiNetworkProvider): ILoginRemoteDS {
        return LoginRemoteDSImpl(
            apiNetworkProvider = apiNetworkProvider
        )
    }

    @Provides
    fun provideLoginLocalDS(localStorageProvider: LocalStorageProvider): ILoginLocalDS {
        return LoginLocalDSImpl(localStorageProvider)
    }

    @Provides
    fun provideLoginRepository(
        loginLocalDS: ILoginLocalDS,
        loginRemoteDS: ILoginRemoteDS,
    ): ILoginRepository {
        return LoginRepositoryImpl(loginLocalDS = loginLocalDS, loginRemoteDS = loginRemoteDS)
    }

    @Provides
    fun providePhoneLoginUseCase(repository: ILoginRepository): PhoneLoginUC {
        return PhoneLoginUC(repository)
    }
}