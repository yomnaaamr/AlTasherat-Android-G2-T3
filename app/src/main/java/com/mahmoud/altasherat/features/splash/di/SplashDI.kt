package com.mahmoud.altasherat.features.splash.di

import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.splash.data.repository.SplashRepository
import com.mahmoud.altasherat.features.splash.data.repository.local.SplashLocalDS
import com.mahmoud.altasherat.features.splash.domain.repository.ISplashRepository
import com.mahmoud.altasherat.features.splash.domain.repository.local.ISplashLocalDS
import com.mahmoud.altasherat.features.splash.domain.usecase.HasUserLoggedInUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
internal object SplashDI {


    @Provides
    fun provideLocalDataSource(
        localStorageProvider: ILocalStorageProvider
    ): ISplashLocalDS {
        return SplashLocalDS(localStorageProvider)
    }

    @Provides
    fun provideSplashRepository(
        localDataSource: ISplashLocalDS
    ): ISplashRepository {
        return SplashRepository(localDataSource)
    }

    @Provides
    fun provideHasUserLoggedInUC(repository: ISplashRepository): HasUserLoggedInUC {
        return HasUserLoggedInUC(repository)

    }
}