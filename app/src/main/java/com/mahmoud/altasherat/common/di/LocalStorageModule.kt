package com.mahmoud.altasherat.common.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mahmoud.altasherat.common.data.local.LocalStorageProvider
import com.mahmoud.altasherat.common.domain.local.ILocalStorageProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {


    @Provides
    @Singleton
    fun provideLocalStorageProvider(@ApplicationContext context: Context): ILocalStorageProvider {
        return LocalStorageProvider(context)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }
}