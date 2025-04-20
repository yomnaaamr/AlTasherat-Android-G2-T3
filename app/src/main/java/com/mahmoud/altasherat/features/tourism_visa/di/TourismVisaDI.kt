package com.mahmoud.altasherat.features.tourism_visa.di

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.tourism_visa.data.repository.TourismVisaRepository
import com.mahmoud.altasherat.features.tourism_visa.data.repository.remote.TourismVisaRemoteDS
import com.mahmoud.altasherat.features.tourism_visa.domain.repository.ITourismVisaRepository
import com.mahmoud.altasherat.features.tourism_visa.domain.repository.remote.ITourismVisaRemoteDS
import com.mahmoud.altasherat.features.tourism_visa.domain.usecase.StoreTourismVisaUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object TourismVisaDI {


    @Provides
    fun provideTourismVisaRemoteDS(
        restApiNetworkProvider: IRestApiNetworkProvider,
        userLocalDataSource: IUserInfoLocalDS

    ): ITourismVisaRemoteDS {
        return TourismVisaRemoteDS(restApiNetworkProvider, userLocalDataSource)
    }

    @Provides
    fun provideTourismVisaRepository(
        remoteDataSource: ITourismVisaRemoteDS
    ): ITourismVisaRepository {
        return TourismVisaRepository(remoteDataSource)
    }

    @Provides
    fun provideStoreTourismVisaUC(repository: ITourismVisaRepository): StoreTourismVisaUC {
        return StoreTourismVisaUC(repository)
    }


}