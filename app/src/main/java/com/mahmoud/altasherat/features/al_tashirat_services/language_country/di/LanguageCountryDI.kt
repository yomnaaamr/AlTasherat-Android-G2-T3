package com.mahmoud.altasherat.features.al_tashirat_services.language_country.di


import com.google.gson.Gson
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.repository.LanguageCountryRepository
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.repository.local.LanguageCountryLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.repository.remote.LanguageCountryRemoteDS
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.ILanguageCountryRepository
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.local.ILanguageCountryLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.remote.ILanguageCountryRemoteDS
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.DeleteSelectedCountryUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetCountriesFromLocalUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetCountriesFromRemoteUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetCountryUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetLanguageCodeUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.HasCountriesUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.SaveSelectedCountryUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.SaveSelectedLanguageUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.SaveSelectionsUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
internal object LanguageCountryDI {


    @Provides
    fun provideRemoteDataSource(
        restApiNetworkProvider: IRestApiNetworkProvider
    ): ILanguageCountryRemoteDS {
        return LanguageCountryRemoteDS(restApiNetworkProvider)
    }

    @Provides
    fun provideLocalDataSource(
        localStorageProvider: ILocalStorageProvider,
        gson: Gson
    ): ILanguageCountryLocalDS {
        return LanguageCountryLocalDS(localStorageProvider, gson)
    }

    @Provides
    fun provideRepository(
        localDataSource: ILanguageCountryLocalDS,
        remoteDataSource: ILanguageCountryRemoteDS
    ): ILanguageCountryRepository {
        return LanguageCountryRepository(remoteDataSource, localDataSource)
    }


    @Provides
    fun provideGetCountriesUC(repository: ILanguageCountryRepository): GetCountriesFromLocalUC {
        return GetCountriesFromLocalUC(repository)
    }

    @Provides
    fun provideSaveSelectionsUC(repository: ILanguageCountryRepository): SaveSelectionsUC {
        return SaveSelectionsUC(repository)
    }

    @Provides
    fun provideGetLanguageCodeUC(repository: ILanguageCountryRepository): GetLanguageCodeUC {
        return GetLanguageCodeUC(repository)
    }

    @Provides
    fun provideFetchCountriesUC(repository: ILanguageCountryRepository): GetCountriesFromRemoteUC {
        return GetCountriesFromRemoteUC(repository)
    }

    @Provides
    fun provideHasCountriesUC(repository: ILanguageCountryRepository): HasCountriesUC {
        return HasCountriesUC(repository)
    }

    @Provides
    fun provideSaveSelectedLanguageUC(repository: ILanguageCountryRepository): SaveSelectedLanguageUC {
        return SaveSelectedLanguageUC(repository)
    }

    @Provides
    fun provideSaveSelectedCountryUC(repository: ILanguageCountryRepository): SaveSelectedCountryUC {
        return SaveSelectedCountryUC(repository)
    }

    @Provides
    fun provideGetCountryUC(repository: ILanguageCountryRepository): GetCountryUC {
        return GetCountryUC(repository)
    }

    @Provides
    fun provideDeleteSelectedCountryUC(repository: ILanguageCountryRepository): DeleteSelectedCountryUC {
        return DeleteSelectedCountryUC(repository)
    }


}