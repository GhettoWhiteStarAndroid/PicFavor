package com.ghettowhitestar.picfavor.presentation

import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghettowhitestar.picfavor.data.PicsumPhoto
import com.ghettowhitestar.picfavor.data.remote.ResultWrapper
import com.ghettowhitestar.picfavor.data.remote.ResultWrapper.*
import com.ghettowhitestar.picfavor.presentation.paginator.Pageable
import com.ghettowhitestar.picfavor.domain.usecases.PhotoUseCase
import com.ghettowhitestar.picfavor.utils.add
import kotlinx.coroutines.*


class PhotoViewModel @ViewModelInject constructor(
    private val useCase: PhotoUseCase,

) : ViewModel(), Pageable {

    override var hasMore: Boolean = true
    override var currentPage: Int = 1
    override var isDownloading: Boolean = false
    override val pageSize: Int = 20

    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main

    private val mutableIsStartNetwork = MutableLiveData<Boolean>()
    val isStartNetwork: LiveData<Boolean>
        get() = mutableIsStartNetwork

    private val mutableToastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = mutableToastMessage


    private val mutableGalleryPhotoList = MutableLiveData<MutableList<PicsumPhoto>>()
    val galleryPhotoList: LiveData<MutableList<PicsumPhoto>>
        get() = mutableGalleryPhotoList

    private val mutableLikedPhotoList = MutableLiveData<MutableList<PicsumPhoto>>()
    val likedPhotoList: LiveData<MutableList<PicsumPhoto>>
        get() = mutableLikedPhotoList

    init {
        getLikedPhoto()
        checkNetwork()
    }

    private fun getLikedPhoto() {
        viewModelScope.launch(dispatcherIO) {
            val likedPhoto = useCase.getLikedPhoto()
            withContext(dispatcherMain) {
                mutableLikedPhotoList.add(likedPhoto)
            }
        }
    }

    fun checkNetwork() {
        mutableIsStartNetwork.value = useCase.checkNetworkConnection()
        if (mutableIsStartNetwork.value?: false) {
            loadNextPage()
        }
    }

    override fun loadNextPage() {
        isDownloading = true
        viewModelScope.launch(dispatcherIO) {
            val listPhotos = (useCase.getGalleryPhoto(currentPage, pageSize))
            withContext(dispatcherMain) {
                isDownloading = false
                when (listPhotos) {
                    is NetworkError -> {
                        mutableToastMessage.value = listPhotos.errorMessage
                    }
                    is GenericError -> {
                        mutableToastMessage.value = "${listPhotos.code} : ${listPhotos.errorMessage}"
                    }
                    is Success<List<PicsumPhoto>> -> {
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

    fun changeLikePhoto(photo: PicsumPhoto, bitmap: Bitmap) {
        viewModelScope.launch(dispatcherIO) {
            photo.isLikedPhoto = !photo.isLikedPhoto
            if (photo.isLikedPhoto) {
                photo.path = "${photo.id}.jpg"
                useCase.likePhoto(bitmap, photo)
                withContext(dispatcherMain) {
                    findLikedPhoto(photo, photo.isLikedPhoto)
                }
            } else {
                useCase.unlikePhoto(photo)
                withContext(dispatcherMain) {
                    findLikedPhoto(photo, photo.isLikedPhoto)
                    removeLikedPhoto(photo)
                }
            }
        }
    }

    private fun findLikedPhoto(photo: PicsumPhoto, isLiked: Boolean) {
        mutableGalleryPhotoList.value?.forEach { item ->
            if (item.id == photo.id) {
                item.isLikedPhoto = isLiked
            }
        }
        mutableGalleryPhotoList.add(listOf())
        mutableLikedPhotoList.add(listOf(photo))
    }

    private fun removeLikedPhoto(photo: PicsumPhoto) {
        mutableLikedPhotoList.value?.removeIf { it.id == photo.id }
        mutableLikedPhotoList.add(listOf())
    }
}
