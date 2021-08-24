package com.ghettowhitestar.picfavor.domain.usecases

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.ConnectivityManager
import com.ghettowhitestar.picfavor.data.remote.PicsumApi
import com.ghettowhitestar.picfavor.data.PicsumPhoto
import com.ghettowhitestar.picfavor.data.local.LikedPhotoDao
import com.ghettowhitestar.picfavor.data.repositories.connection.ConnectionRepository
import com.ghettowhitestar.picfavor.data.repositories.connection.NetworkConnectionRepository
import com.ghettowhitestar.picfavor.data.repositories.files.FileStorageRepository
import com.ghettowhitestar.picfavor.data.repositories.photos.local.LocalPhotoRepository
import com.ghettowhitestar.picfavor.data.repositories.photos.network.NetworkGalleryPhotoRepository
import com.ghettowhitestar.picfavor.data.repositories.photos.network.NetworkPhotoRepository
import com.ghettowhitestar.picfavor.utils.Constants.PAGE_SIZE



import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoUseCase @Inject constructor(
    private val localPhotoRepository: LocalPhotoRepository,
    private val networkPhotoRepository: NetworkPhotoRepository,
    private val connectionRepository: ConnectionRepository,
    private val fileStorageRepository: FileStorageRepository
) {

    fun getLikedPhoto() =
        localPhotoRepository.getLikesPhotoResult()


    fun checkNetworkConnection() = connectionRepository.isNetworkAvailable()


    suspend fun getGalleryPhoto(currentPage: Int) =
        networkPhotoRepository.getGalleryPhotosResult(PAGE_SIZE, currentPage)


   fun isGalleryPhotoLiked(listPhoto: List<PicsumPhoto>,listLikedPhoto: List<PicsumPhoto>): List<PicsumPhoto> {
        for (item: PicsumPhoto in listPhoto) {
            for (liked: PicsumPhoto in listLikedPhoto) {
                if (item.id == liked.id) {
                    item.isLikedPhoto = true
                }
            }
        }
        return listPhoto
    }

    internal fun likePhoto(bitmap: Bitmap, photo: PicsumPhoto) {
        fileStorageRepository.saveImage(bitmap, photo)
        localPhotoRepository.insertLikedPhoto(photo)
    }

    internal fun unlikePhoto(photo: PicsumPhoto) {
        fileStorageRepository.deleteImage("${photo.id}.jpg")
        localPhotoRepository.deleteLikedPhoto(photo)
    }}