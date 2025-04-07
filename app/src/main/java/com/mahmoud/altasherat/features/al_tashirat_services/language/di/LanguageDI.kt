package com.mahmoud.altasherat.features.al_tashirat_services.language.di

import com.google.gson.Gson
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.al_tashirat_services.language.data.repository.LanguageRepository
import com.mahmoud.altasherat.features.al_tashirat_services.language.data.repository.local.LanguageLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.repository.ILanguageRepository
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.repository.local.ILanguageLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.usecase.GetLanguageCodeUC
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.usecase.SaveSelectedLanguageUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object LanguageDI {


    @Provides
    fun provideLocalDataSource(
        localStorageProvider: ILocalStorageProvider,
        gson: Gson
    ): ILanguageLocalDS {
        return LanguageLocalDS(localStorageProvider, gson)
    }

    @Provides
    fun provideRepository(
        localDataSource: ILanguageLocalDS,
    ): ILanguageRepository {
        return LanguageRepository(localDataSource)
    }


    @Provides
    fun provideGetLanguageCodeUC(repository: ILanguageRepository): GetLanguageCodeUC {
        return GetLanguageCodeUC(repository)
    }


    @Provides
    fun provideSaveSelectedLanguageUC(repository: ILanguageRepository): SaveSelectedLanguageUC {
        return SaveSelectedLanguageUC(repository)
    }
}