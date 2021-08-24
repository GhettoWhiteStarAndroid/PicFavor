package com.ghettowhitestar.picfavor.presentation

import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghettowhitestar.picfavor.data.PicsumPhoto
import com.ghettowhitestar.picfavor.data.remote.ResultWrapper
import com.ghettowhitestar.picfavor.presentation.paginator.Pageable
import com.ghettowhitestar.picfavor.domain.usecases.PhotoUseCase
import com.ghettowhitestar.picfavor.utils.add
import com.ghettowhitestar.picfavor.utils.delete
import kotlinx.coroutines.*

class PhotoViewModel @ViewModelInject constructor(
    private val useCase: PhotoUseCase
) : ViewModel(), Pageable {

    override var hasMore: Boolean = true
    override var currentPage: Int = 1
    override var isDownloading: Boolean = false

    private val mutableIsStartNetwork = MutableLiveData<Boolean>()
    val isStartNetwork: LiveData<Boolean>
        get() = mutableIsStartNetwork

    private val mutableGalleryPhotoList = MutableLiveData<MutableList<PicsumPhoto>>()
    val galleryPhotoList: LiveData<MutableList<PicsumPhoto>>
        get() = mutableGalleryPhotoList

    private val mutableLikedPhotoList = MutableLiveData<MutableList<PicsumPhoto>>()
    val likedPhotoList: LiveData<MutableList<PicsumPhoto>>
        get() = mutableLikedPhotoList

    /*  private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO + exceptionHandler*/

    init {
        getLikedPhoto()
    }

    private fun getLikedPhoto() {
        viewModelScope.launch(Dispatchers.IO) {
            val likedPhoto = useCase.getLikedPhoto()
            withContext(Dispatchers.Main) {
                mutableLikedPhotoList.add(likedPhoto)
                checkNetwork()
            }
            loadNextPage()
        }
    }

    fun checkNetwork(){
        mutableIsStartNetwork.value = useCase.checkNetworkConnection()
    }

    fun changeLikePhoto(photo: PicsumPhoto, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            if (photo.isLikedPhoto) {
                photo.isLikedPhoto = !photo.isLikedPhoto
                useCase.unlikePhoto(photo)
                withContext(Dispatchers.Main) {
                    findLikedPhoto(photo,photo.isLikedPhoto)
                    findPhoto(photo)
                }
            } else {
                photo.isLikedPhoto = !photo.isLikedPhoto
                photo.path = "${photo.id}.jpg"
                useCase.likePhoto(bitmap, photo)
                withContext(Dispatchers.Main) {
                    findLikedPhoto(photo,photo.isLikedPhoto)
                    mutableLikedPhotoList.add(listOf(photo))
                }
            }
        }
    }

    override fun loadNextPage() {
        isDownloading = true
        viewModelScope.launch(Dispatchers.IO) {
            val listPhotos = (useCase.getGalleryPhoto(currentPage))
            withContext(Dispatchers.Main) {
                isDownloading = false
                when (listPhotos) {
                    is ResultWrapper.NetworkError -> {
                    }
                    is ResultWrapper.GenericError -> {
                    }
                    is ResultWrapper.Success<List<PicsumPhoto>> -> {
                        currentPage++
                        mutableGalleryPhotoList.add(
                            useCase.isGalleryPhotoLiked(
                                listPhotos.value,
                                likedPhotoList.value ?: listOf()
                            )
                        )
                    }
                }
            }
        }
    }
    private fun findLikedPhoto(photo: PicsumPhoto, isLiked: Boolean) {
        for (item: PicsumPhoto in mutableGalleryPhotoList.value ?: listOf()) {
            if (item.id == photo.id) {
                item.isLikedPhoto = isLiked
            }
        }
        mutableGalleryPhotoList.add(listOf())
    }

    private fun findPhoto(photo: PicsumPhoto) {
        mutableLikedPhotoList.value?.removeIf { it.id == photo.id }
        mutableLikedPhotoList.add(listOf())
    }
}
