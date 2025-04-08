package com.mahmoud.altasherat.features.menu_options.change_password.di

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.menu_options.change_password.data.repository.ChangePassRepository
import com.mahmoud.altasherat.features.menu_options.change_password.data.repository.remote.ChangePassRemoteDS
import com.mahmoud.altasherat.features.menu_options.change_password.domain.repository.IChangePassRepository
import com.mahmoud.altasherat.features.menu_options.change_password.domain.repository.remote.IChangePassRemoteDS
import com.mahmoud.altasherat.features.menu_options.change_password.domain.usecase.ChangePasswordUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ChangePasswordModule {

    @Provides
    fun provideChangePasswordRemoteDS(
        apiNetworkProvider: IRestApiNetworkProvider
    ): IChangePassRemoteDS {
        return ChangePassRemoteDS(
            networkProvider = apiNetworkProvider,
        )
    }


    @Provides
    fun provideChangePasswordRepository(
        deleteAccDS: IChangePassRemoteDS,
        localDS: IUserInfoLocalDS
    ): IChangePassRepository {
        return ChangePassRepository(
            changePassRemoteDS = deleteAccDS,
            userLocalDS = localDS,
        )
    }

    @Provides
    fun provideChangePasswordUseCase(repository: IChangePassRepository): ChangePasswordUC {
        return ChangePasswordUC(repository)
    }
}