package com.mahmoud.altasherat.features.onBoarding.onboarding.di

import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.onBoarding.onboarding.data.repository.OnBoardingRepositoryImpl
import com.mahmoud.altasherat.features.onBoarding.onboarding.data.repository.local.OnBoardingLocalDS
import com.mahmoud.altasherat.features.onBoarding.onboarding.domain.repository.IOnBoardingRepository
import com.mahmoud.altasherat.features.onBoarding.onboarding.domain.repository.local.IOnBoardingLocalDS
import com.mahmoud.altasherat.features.onBoarding.onboarding.domain.useCase.GetOnBoardingStateUC
import com.mahmoud.altasherat.features.onBoarding.onboarding.domain.useCase.SetOnBoardingStateUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
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
    ): SetOnBoardingStateUC {
        return SetOnBoardingStateUC(onBoardingRepository)
    }

    @Provides
    fun provideIsFirstTimeToLaunchTheAppUC(
        onBoardingRepository: IOnBoardingRepository
    ): GetOnBoardingStateUC {
        return GetOnBoardingStateUC(onBoardingRepository)
    }
}