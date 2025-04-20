package com.mahmoud.altasherat.features.authentication.signup.di

import com.google.gson.Gson
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.authentication.signup.data.repository.SignupRepository
import com.mahmoud.altasherat.features.authentication.signup.data.repository.local.SignupLocalDS
import com.mahmoud.altasherat.features.authentication.signup.data.repository.remote.SignupRemoteDS
import com.mahmoud.altasherat.features.authentication.signup.domain.repository.ISignupRepository
import com.mahmoud.altasherat.features.authentication.signup.domain.repository.local.ISignupLocalDS
import com.mahmoud.altasherat.features.authentication.signup.domain.repository.remote.ISignupRemoteDS
import com.mahmoud.altasherat.features.authentication.signup.domain.usecase.SignupUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
internal object SignupDI {

    @Provides
    fun provideRemoteDataSource(restApiNetworkProvider: IRestApiNetworkProvider): ISignupRemoteDS {
        return SignupRemoteDS(restApiNetworkProvider)
    }


    @Provides
    fun provideLocalDataSource(
        localStorageProvider: ILocalStorageProvider,
        gson: Gson
    ): ISignupLocalDS {
        return SignupLocalDS(localStorageProvider, gson)
    }


    @Provides
    fun provideSignupRepository(
        remoteDataSource: ISignupRemoteDS,
        localDataSource: ISignupLocalDS
    ): ISignupRepository {
        return SignupRepository(
            remoteDataSource,
            localDataSource
        )
    }


    @Provides
    fun provideSignupUC(repository: ISignupRepository): SignupUC {
        return SignupUC(repository)
    }

}