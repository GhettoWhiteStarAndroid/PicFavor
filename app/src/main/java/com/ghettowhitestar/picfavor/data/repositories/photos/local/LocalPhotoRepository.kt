package com.ghettowhitestar.picfavor.data.repositories.photos.local

import com.ghettowhitestar.picfavor.data.PicsumPhoto

interface LocalPhotoRepository {
    fun getLikesPhotoResult() : List<PicsumPhoto>
    fun insertLikedPhoto(photo: PicsumPhoto)
    fun deleteLikedPhoto(photo: PicsumPhoto)
}