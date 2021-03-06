package com.ghettowhitestar.picfavor.data.repositories.photos.network

import android.net.ConnectivityManager
import com.ghettowhitestar.picfavor.data.remote.NetworkHelper.safeApiCall
import com.ghettowhitestar.picfavor.data.remote.PicsumApi
import javax.inject.Inject

class NetworkGalleryPhotoRepository @Inject constructor(
    private val picsumApi: PicsumApi,
    private val connectivityManager: ConnectivityManager
) : NetworkPhotoRepository {

    override fun isNetworkAvailable() = connectivityManager.activeNetwork != null

    override suspend fun getGalleryPhotosResult(pageSize: Int, currentPage: Int) =
        safeApiCall{picsumApi.getListGalleryPhotos(currentPage, pageSize)}
}