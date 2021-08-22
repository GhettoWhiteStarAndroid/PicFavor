package com.ghettowhitestar.picfavor.data.repositories.photos.local

import com.ghettowhitestar.picfavor.data.PicsumPhoto
import com.ghettowhitestar.picfavor.data.local.LikedPhotoDao
import javax.inject.Inject

class LocalLikedPhotoRepository @Inject constructor(
    val likedPhotoDao: LikedPhotoDao
    ) : LocalPhotoRepository {

    override fun getLikesPhotoResult() =
        likedPhotoDao.getAllLikedPhotos()

    override fun insertLikedPhoto(photo: PicsumPhoto) =
        likedPhotoDao.insertLikedPhoto(photo)

    override fun deleteLikedPhoto(photo: PicsumPhoto) =
        likedPhotoDao.deleteLikedPhoto(photo)

}