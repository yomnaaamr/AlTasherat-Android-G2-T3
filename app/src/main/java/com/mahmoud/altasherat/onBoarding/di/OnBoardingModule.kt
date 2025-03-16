package com.mahmoud.altasherat.onBoarding.di

import com.mahmoud.altasherat.common.data.repository.local.LocalStorageProvider
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.onBoarding.data.local.OnBoardingLocalDS
import com.mahmoud.altasherat.onBoarding.data.repository.OnBoardingRepositoryImpl
import com.mahmoud.altasherat.onBoarding.domain.local.IOnBoardingLocalDS
import com.mahmoud.altasherat.onBoarding.domain.repository.IOnBoardingRepository
import com.mahmoud.altasherat.onBoarding.domain.useCase.GetOnBoardingVisibilityUC
import com.mahmoud.altasherat.onBoarding.domain.useCase.SaveOnBoardingVisibilityUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.FragmentScoped
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
    fun provideSaveOnBoardingUseCase(
        onBoardingRepository: IOnBoardingRepository
    ): SaveOnBoardingVisibilityUC {
        return SaveOnBoardingVisibilityUC(onBoardingRepository)
    }

    @Provides
    fun provideGetOnBoardingVisibilityUseCase(
        onBoardingRepository: IOnBoardingRepository
    ): GetOnBoardingVisibilityUC {
        return GetOnBoardingVisibilityUC(onBoardingRepository)
    }
}