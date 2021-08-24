package com.ghettowhitestar.picfavor.data.remote

import com.ghettowhitestar.picfavor.data.PicsumPhoto
import retrofit2.http.GET
import retrofit2.http.Query

/** Описание запросов к API PicsumPhoto */
interface PicsumApi {

    /**
     * Запрос на получение списка фотографий
     * @param page страница подгружаемых фотографий
     * @param limit кол-во получаемых фотографий на одной странице
     */
    @GET("v2/list")
    suspend fun getListGalleryPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<PicsumPhoto>

    companion object {
        const val BASE_URL = "https://picsum.photos/"
    }
}