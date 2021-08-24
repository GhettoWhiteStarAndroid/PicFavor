package com.ghettowhitestar.picfavor.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/** Объкт хранимый в базе данных и POJO для запросов */
@Entity
@Parcelize
data class PicsumPhoto(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @ColumnInfo(name = "author")
    val author: String,
    @SerializedName("download_url")
    val downloadUrl: String,
    var isLikedPhoto: Boolean = false,
    @ColumnInfo(name = "path")
    var path: String
) : Parcelable
{

    fun copy(isLike: Boolean) =
        PicsumPhoto(
            this.id,
            this.author,
            this.downloadUrl,
            isLike,
            this.path
        )
}
