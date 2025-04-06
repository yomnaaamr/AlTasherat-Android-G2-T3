package com.mahmoud.altasherat.features.update_account.di

import com.google.gson.Gson
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.update_account.data.repository.UpdateAccRepository
import com.mahmoud.altasherat.features.update_account.data.repository.local.UpdateAccLocalDS
import com.mahmoud.altasherat.features.update_account.data.repository.remote.UpdateAccRemoteDS
import com.mahmoud.altasherat.features.update_account.domain.repository.IUpdateAccRepository
import com.mahmoud.altasherat.features.update_account.domain.repository.local.IUpdateAccLocalDS
import com.mahmoud.altasherat.features.update_account.domain.repository.remote.IUpdateAccRemoteDS
import com.mahmoud.altasherat.features.update_account.domain.usecase.UpdateAccountUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object UpdateAccountDI {


    @Provides
    fun provideUpdateAccRemoteDS(
        restApiNetworkProvider: IRestApiNetworkProvider
    ): IUpdateAccRemoteDS {
        return UpdateAccRemoteDS(restApiNetworkProvider)
    }

    @Provides
    fun provideUpdateAccLocalDS(
        localStorageProvider: ILocalStorageProvider,
        gson: Gson
    ): IUpdateAccLocalDS {
        return UpdateAccLocalDS(localStorageProvider, gson)
    }

    @Provides
    fun provideUpdateAccRepository(
        updateLocalDataSource: IUpdateAccLocalDS,
        userLocalDataSource: IUserInfoLocalDS,
        remoteDataSource: IUpdateAccRemoteDS
    ): IUpdateAccRepository {
        return UpdateAccRepository(remoteDataSource, updateLocalDataSource, userLocalDataSource)
    }

    @Provides
    fun provideUpdateUserInfoUC(repository: IUpdateAccRepository): UpdateAccountUC {
        return UpdateAccountUC(repository)
    }


}