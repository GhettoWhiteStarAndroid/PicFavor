package com.ghettowhitestar.picfavor.data.repositories.photos.network

import com.ghettowhitestar.picfavor.data.PicsumPhoto
import com.ghettowhitestar.picfavor.data.remote.ResultWrapper

interface NetworkPhotoRepository {

   fun isNetworkAvailable() : Boolean

   suspend fun getGalleryPhotosResult(pageSize: Int, currentPage: Int) : ResultWrapper <List<PicsumPhoto>>
}