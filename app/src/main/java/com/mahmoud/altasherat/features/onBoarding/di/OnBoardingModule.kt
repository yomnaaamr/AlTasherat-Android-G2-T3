package com.mahmoud.altasherat.features.onBoarding.di

import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.onBoarding.data.repository.local.OnBoardingLocalDS
import com.mahmoud.altasherat.features.onBoarding.data.repository.OnBoardingRepositoryImpl
import com.mahmoud.altasherat.features.onBoarding.domain.repository.local.IOnBoardingLocalDS
import com.mahmoud.altasherat.features.onBoarding.domain.repository.IOnBoardingRepository
import com.mahmoud.altasherat.features.onBoarding.domain.useCase.GetOnBoardingStateUC
import com.mahmoud.altasherat.features.onBoarding.domain.useCase.SetOnBoardingStateUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object OnBoardingModule {

    @Provides
    fun provideOnBoardingLocalDS(localStorageProvider: ILocalStorageProvider): IOnBoardingLocalDS {
        return OnBoardingLocalDS(localStorageProvider)
    }
    @Provides
    fun provideOnBoardingRepository(onBoardingLocalDS: IOnBoardingLocalDS): IOnBoardingRepository {
        return OnBoardingRepositoryImpl(onBoardingLocalDS)
    }
    @Provides
    fun provideSetOnBoardingAsShownUC(
        onBoardingRepository: IOnBoardingRepository
    ):SetOnBoardingStateUC {
        return SetOnBoardingStateUC(onBoardingRepository)
    }

    @Provides
    fun provideIsFirstTimeToLaunchTheAppUC(
        onBoardingRepository: IOnBoardingRepository
    ): GetOnBoardingStateUC {
        return GetOnBoardingStateUC(onBoardingRepository)
    }
}