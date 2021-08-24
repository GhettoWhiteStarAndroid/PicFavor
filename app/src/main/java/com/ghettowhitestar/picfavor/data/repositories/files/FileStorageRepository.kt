package com.ghettowhitestar.picfavor.data.repositories.files

import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Environment
import com.ghettowhitestar.picfavor.data.PicsumPhoto
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

class FileStorageRepository @Inject constructor(private val filePath:String) : FileRepository {

    /**
     * Удаление фотографии из памяти телефона
     * @ path путь к сохраненной фотографии
     */
    override fun deleteImage(path: String) {

        val storageDir = File(
            filePath
        )
        val imageFile = File(storageDir, path)
        if (imageFile.exists()) {
            imageFile.delete()
        }
    }

    /** Сохранение фотографии на телефоне */
    override fun saveImage(image: Bitmap, photo: PicsumPhoto) {
        val storageDir = File(
            filePath
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, photo.path)
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 30, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}