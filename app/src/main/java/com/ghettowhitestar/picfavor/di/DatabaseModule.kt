package com.ghettowhitestar.picfavor.di

import android.content.Context
import androidx.room.Room
import com.ghettowhitestar.picfavor.data.local.AppDatabase
import com.ghettowhitestar.picfavor.data.local.LikedPhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideLikedPhotoDao(appDatabase: AppDatabase): LikedPhotoDao {
        return appDatabase.likedPhotoDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "picsum"
        ).build()
    }
}