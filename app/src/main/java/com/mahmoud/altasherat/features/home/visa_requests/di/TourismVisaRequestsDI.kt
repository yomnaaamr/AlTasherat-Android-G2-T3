package com.mahmoud.altasherat.features.home.visa_requests.di

import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository.local.IUserInfoLocalDS
import com.mahmoud.altasherat.features.home.visa_requests.data.repository.TourismVisaRequestsRepository
import com.mahmoud.altasherat.features.home.visa_requests.data.repository.remote.TourismVisaRequestsRemoteDS
import com.mahmoud.altasherat.features.home.visa_requests.domain.repository.ITourismVisaRequestsRepository
import com.mahmoud.altasherat.features.home.visa_requests.domain.repository.remote.ITourismVisaRequestsRemoteDS
import com.mahmoud.altasherat.features.home.visa_requests.domain.useCases.GetTourismVisaRequestsUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object TourismVisaRequestsDI {


    @Provides
    fun provideRemoteDataSource(
        restApiNetworkProvider: IRestApiNetworkProvider,
        userLocalDS: IUserInfoLocalDS
    ): ITourismVisaRequestsRemoteDS {
        return TourismVisaRequestsRemoteDS(restApiNetworkProvider,userLocalDS)
    }


    @Provides
    fun provideTourismVisaRequestsRepository(
        remoteDataSource: ITourismVisaRequestsRemoteDS,
    ): ITourismVisaRequestsRepository {
        return TourismVisaRequestsRepository(
            remoteDataSource
        )
    }


    @Provides
    fun provideGetTourismVisaRequestsUC(repository: ITourismVisaRequestsRepository): GetTourismVisaRequestsUC {
        return GetTourismVisaRequestsUC(repository)
    }
}