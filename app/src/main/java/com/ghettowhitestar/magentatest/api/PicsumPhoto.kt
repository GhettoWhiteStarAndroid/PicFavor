package com.ghettowhitestar.magentatest.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PicsumPhoto(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String
) : Parcelable
