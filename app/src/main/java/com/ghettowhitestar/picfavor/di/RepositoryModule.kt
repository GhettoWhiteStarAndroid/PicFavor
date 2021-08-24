package com.ghettowhitestar.picfavor.di

import com.ghettowhitestar.picfavor.data.repositories.files.FileRepository
import com.ghettowhitestar.picfavor.data.repositories.files.FileStorageRepository
import com.ghettowhitestar.picfavor.data.repositories.photos.local.LocalLikedPhotoRepository
import com.ghettowhitestar.picfavor.data.repositories.photos.local.LocalPhotoRepository
import com.ghettowhitestar.picfavor.data.repositories.photos.network.NetworkGalleryPhotoRepository
import com.ghettowhitestar.picfavor.data.repositories.photos.network.NetworkPhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindFileRepository(fileStorageRepository: FileStorageRepository): FileRepository

    @Binds
    abstract fun bindLocalPhotoRepository(localLikedPhotoRepository: LocalLikedPhotoRepository): LocalPhotoRepository

    @Binds
    abstract fun bindNetworkPhotoRepository(networkGalleryPhotoRepository: NetworkGalleryPhotoRepository): NetworkPhotoRepository

}