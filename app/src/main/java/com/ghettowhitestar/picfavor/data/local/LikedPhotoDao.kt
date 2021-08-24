package com.ghettowhitestar.picfavor.data.local

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
    fun getAllLikedPhotos(): List<PicsumPhoto>

    /**Добавление понравившейся фотографии*/
    @Insert
    fun insertLikedPhoto(likedPhoto: PicsumPhoto)

    /**Удаление понравившейся фотографии*/
    @Query("DELETE FROM PicsumPhoto WHERE id = :id")
    fun deleteLikedPhoto(id: Long)
}