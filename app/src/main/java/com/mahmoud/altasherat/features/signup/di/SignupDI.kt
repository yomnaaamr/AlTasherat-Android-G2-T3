package com.mahmoud.altasherat.features.signup.di

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.signup.data.repository.SignupRepository
import com.mahmoud.altasherat.features.signup.data.repository.remote.SignupRemoteDS
import com.mahmoud.altasherat.features.signup.domain.repository.ISignupRepository
import com.mahmoud.altasherat.features.signup.domain.repository.remote.ISignupRemoteDS
import com.mahmoud.altasherat.features.signup.domain.usecase.SignupUC
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
    fun provideSignupRepository(
        remoteDataSource: ISignupRemoteDS,
    ): ISignupRepository {
        return SignupRepository(remoteDataSource)
    }


    @Provides
    fun provideSignupUC(repository: ISignupRepository): SignupUC {
        return SignupUC(repository)
    }

}