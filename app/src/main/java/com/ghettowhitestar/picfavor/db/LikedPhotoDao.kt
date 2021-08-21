package com.ghettowhitestar.picfavor.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import com.ghettowhitestar.picfavor.data.PicsumPhoto
import io.reactivex.Single

/**Методы для работы с базой данных*/
@Dao
interface LikedPhotoDao {

    /**Получение всех понравившихся фотографий*/
    @Query("SELECT * FROM PicsumPhoto")
    fun getAllLikedPhotos(): Single<List<PicsumPhoto>>

    /**Добавление понравившейся фотографии*/
    @Insert
    fun insertLikedPhoto(likedPhoto: PicsumPhoto)

    /**Удаление понравившейся фотографии*/
    @Delete
    fun deleteLikedPhoto(likedPhoto: PicsumPhoto)
}