package com.ghettowhitestar.picfavor.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ghettowhitestar.picfavor.data.PicsumPhoto

/**Класс описание структуры базы данных*/
@Database(entities = [PicsumPhoto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun likedPhotoDao(): LikedPhotoDao
}