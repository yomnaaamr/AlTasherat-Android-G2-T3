package com.mahmoud.altasherat.features.splash.di

import com.google.gson.Gson
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.splash.data.repository.SplashRepository
import com.mahmoud.altasherat.features.splash.data.repository.local.SplashLocalDS
import com.mahmoud.altasherat.features.splash.data.repository.remote.SplashRemoteDS
import com.mahmoud.altasherat.features.splash.domain.repository.ISplashRepository
import com.mahmoud.altasherat.features.splash.domain.repository.local.ISplashLocalDS
import com.mahmoud.altasherat.features.splash.domain.repository.remote.ISplashRemoteDS
import com.mahmoud.altasherat.features.splash.domain.usecase.FetchCountriesUC
import com.mahmoud.altasherat.features.splash.domain.usecase.HasUserLoggedInUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
internal object SplashDI {

    @Provides
    fun provideRemoteDataSource(restApiNetworkProvider: IRestApiNetworkProvider): ISplashRemoteDS {
        return SplashRemoteDS(restApiNetworkProvider)
    }


    @Provides
    fun provideLocalDataSource(
        localStorageProvider: ILocalStorageProvider,
        gson: Gson
    ): ISplashLocalDS {
        return SplashLocalDS(localStorageProvider, gson)
    }

    @Provides
    fun provideSplashRepository(
        remoteDataSource: ISplashRemoteDS,
        localDataSource: ISplashLocalDS
    ): ISplashRepository {
        return SplashRepository(remoteDataSource, localDataSource)
    }


    @Provides
    fun provideFetchCountriesUC(repository: ISplashRepository): FetchCountriesUC {
        return FetchCountriesUC(repository)
    }

    @Provides
    fun provideHasUserLoggedInUC(repository: ISplashRepository): HasUserLoggedInUC {
        return HasUserLoggedInUC(repository)

    }


}