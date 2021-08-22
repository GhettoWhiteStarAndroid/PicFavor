package com.ghettowhitestar.picfavor.data.repositories.files

import android.graphics.Bitmap
import com.ghettowhitestar.picfavor.data.PicsumPhoto

interface FileRepository {
    fun deleteImage(path: String)
    fun saveImage(image: Bitmap, photo: PicsumPhoto)
}