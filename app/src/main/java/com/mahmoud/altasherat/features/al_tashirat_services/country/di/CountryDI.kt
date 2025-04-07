package com.mahmoud.altasherat.features.al_tashirat_services.country.di


import com.google.gson.Gson
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.al_tashirat_services.country.data.repository.CountryRepository
import com.mahmoud.altasherat.features.al_tashirat_services.country.data.repository.local.CountryLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.country.data.repository.remote.CountryRemoteDS
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.ICountryRepository
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.local.ICountryLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.remote.ICountryRemoteDS
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.repository.ILanguageRepository
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.GetCountriesFromLocalUC
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.GetCountriesFromRemoteUC
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.GetCountryUC
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.HasCountriesUC
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.SaveSelectedCountryUC
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.SaveSelectionsUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
internal object CountryDI {


    @Provides
    fun provideRemoteDataSource(
        restApiNetworkProvider: IRestApiNetworkProvider
    ): ICountryRemoteDS {
        return CountryRemoteDS(restApiNetworkProvider)
    }

    @Provides
    fun provideLocalDataSource(
        localStorageProvider: ILocalStorageProvider,
        gson: Gson
    ): ICountryLocalDS {
        return CountryLocalDS(localStorageProvider, gson)
    }

    @Provides
    fun provideRepository(
        localDataSource: ICountryLocalDS,
        remoteDataSource: ICountryRemoteDS
    ): ICountryRepository {
        return CountryRepository(remoteDataSource, localDataSource)
    }


    @Provides
    fun provideGetCountriesUC(repository: ICountryRepository): GetCountriesFromLocalUC {
        return GetCountriesFromLocalUC(repository)
    }

    @Provides
    fun provideSaveSelectionsUC(repository: ICountryRepository,languageRepository: ILanguageRepository): SaveSelectionsUC {
        return SaveSelectionsUC(repository,languageRepository)
    }



    @Provides
    fun provideFetchCountriesUC(repository: ICountryRepository): GetCountriesFromRemoteUC {
        return GetCountriesFromRemoteUC(repository)
    }

    @Provides
    fun provideHasCountriesUC(repository: ICountryRepository): HasCountriesUC {
        return HasCountriesUC(repository)
    }



    @Provides
    fun provideSaveSelectedCountryUC(repository: ICountryRepository): SaveSelectedCountryUC {
        return SaveSelectedCountryUC(repository)
    }

    @Provides
    fun provideGetCountryUC(repository: ICountryRepository): GetCountryUC {
        return GetCountryUC(repository)
    }


}