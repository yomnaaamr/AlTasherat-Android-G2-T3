package com.mahmoud.altasherat.features.delete_account.di

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.delete_account.data.repository.DeleteAccountRepository
import com.mahmoud.altasherat.features.delete_account.data.repository.remote.DeleteAccountRemoteDS
import com.mahmoud.altasherat.features.delete_account.domain.repository.IDeleteAccountRepository
import com.mahmoud.altasherat.features.delete_account.domain.repository.remote.IDeleteAccountRemoteDS
import com.mahmoud.altasherat.features.delete_account.domain.usecase.DeleteAccountUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DeleteAccountModule {

    @Provides
    fun provideDeleteAccRemoteDS(
        apiNetworkProvider: IRestApiNetworkProvider
    ): IDeleteAccountRemoteDS {
        return DeleteAccountRemoteDS(
            restApiNetworkProvider = apiNetworkProvider,
        )
    }


    @Provides
    fun provideDeleteAccRepository(
        userLocalDS: IUserInfoLocalDS,
        deleteAccDS: IDeleteAccountRemoteDS,
    ): IDeleteAccountRepository {
        return DeleteAccountRepository(
            deleteAccDS = deleteAccDS,
            userLocalDS = userLocalDS
        )
    }

    @Provides
    fun provideDeleteAccountUseCase(repository: IDeleteAccountRepository): DeleteAccountUC {
        return DeleteAccountUC(repository)
    }
}