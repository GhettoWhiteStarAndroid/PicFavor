package com.ghettowhitestar.picfavor.domain

import android.graphics.Bitmap
import android.net.ConnectivityManager
import com.ghettowhitestar.picfavor.data.remote.PicsumApi
import com.ghettowhitestar.picfavor.data.PicsumPhoto
import com.ghettowhitestar.picfavor.data.local.LikedPhotoDao


import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(
    private val picsumApi: PicsumApi,
    private val likedPhotoDao: LikedPhotoDao,
    private val connectivityManager: ConnectivityManager,
    private val fileRepository: FileRepository
) {

    fun getGalleryPhotosResult(pageSize: Int, currentPage: Int) =
        picsumApi.getListGalleryPhotos(currentPage, pageSize)

    fun getLikesPhotoResult() =
        likedPhotoDao.getAllLikedPhotos()

    fun insertLikedPhoto(photo: PicsumPhoto) =
        likedPhotoDao.insertLikedPhoto(photo)

    fun deleteLikedPhoto(photo: PicsumPhoto) =
        likedPhotoDao.deleteLikedPhoto(photo)

    fun isNetworkAvailable() = connectivityManager.activeNetwork == null

    fun saveImage(image: Bitmap, photo: PicsumPhoto) = fileRepository.saveImage(image, photo)

    fun deleteImage(path: String) = fileRepository.deleteImage(path)
}