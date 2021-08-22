package com.ghettowhitestar.picfavor.data.repositories.photos.network

import com.ghettowhitestar.picfavor.data.remote.NetworkHelper.safeApiCall
import com.ghettowhitestar.picfavor.data.remote.PicsumApi
import javax.inject.Inject

class NetworkGalleryPhotoRepository @Inject constructor(val picsumApi: PicsumApi) : NetworkPhotoRepository {
    override suspend fun getGalleryPhotosResult(pageSize: Int, currentPage: Int) =
        safeApiCall{picsumApi.getListGalleryPhotos(currentPage, pageSize)}
}