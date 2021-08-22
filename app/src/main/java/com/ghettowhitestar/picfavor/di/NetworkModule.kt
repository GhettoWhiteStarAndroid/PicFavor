package com.ghettowhitestar.picfavor.di

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
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
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(PicsumApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    
    @Provides
    @Singleton
    fun checkNetwork(@ApplicationContext appContext: Context): ConnectivityManager {
        var connectivity: ConnectivityManager? = null
        connectivity =
            appContext.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivity
    }

    @Provides
    @Singleton
    fun providePicsumApi(retrofit: Retrofit): PicsumApi =
        retrofit.create(PicsumApi::class.java)

}