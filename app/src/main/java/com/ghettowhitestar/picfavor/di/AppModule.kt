package com.ghettowhitestar.picfavor.di

import android.content.Context
import com.ghettowhitestar.picfavor.data.remote.PicsumApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideFilePath(@ApplicationContext appContext: Context): String = "${appContext.filesDir.absolutePath}/upload"

}