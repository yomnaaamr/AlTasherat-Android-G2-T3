package com.mahmoud.altasherat.features.language_country.di


import com.google.gson.Gson
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.language_country.data.repository.LanguageCountryRepository
import com.mahmoud.altasherat.features.language_country.data.repository.local.LanguageCountryLocalDS
import com.mahmoud.altasherat.features.language_country.domain.repository.ILanguageCountryRepository
import com.mahmoud.altasherat.features.language_country.domain.repository.local.ILanguageCountryLocalDS
import com.mahmoud.altasherat.features.language_country.domain.usecase.GetCountriesUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
internal object LanguageCountryDI {


    @Provides
    fun provideLocalDataSource(
        localStorageProvider: ILocalStorageProvider,
        gson: Gson
    ): ILanguageCountryLocalDS {
        return LanguageCountryLocalDS(localStorageProvider, gson)
    }

    @Provides
    fun provideRepository(
        localDataSource: ILanguageCountryLocalDS
    ): ILanguageCountryRepository {
        return LanguageCountryRepository(localDataSource)
    }


    @Provides
    fun provideGetCountriesUC(repository: ILanguageCountryRepository): GetCountriesUC {
        return GetCountriesUC(repository)
    }


}