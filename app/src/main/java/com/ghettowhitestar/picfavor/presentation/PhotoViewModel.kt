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
                mutableIsStartNetwork.value = useCase.checkNetworkConnection()
            }
            loadNextPage()
        }
    }

    fun changeLikePhoto(photo: PicsumPhoto, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            if (photo.isLikedPhoto) {
                useCase.unlikePhoto(photo)
                withContext(Dispatchers.Main) {
                    mutableLikedPhotoList.delete(listOf(photo))
                    findLikedPhoto(photo,false)
                }
            } else {
                photo.path = "${photo.id}.jpg"
                useCase.likePhoto(bitmap, photo)
                withContext(Dispatchers.Main) {
                    mutableLikedPhotoList.add(listOf(photo))
                    findLikedPhoto(photo,true)
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

    fun findLikedPhoto(photo: PicsumPhoto, isLike: Boolean) {
        val list = mutableGalleryPhotoList.value?.toMutableList()
        list?.forEachIndexed { index, item ->
            if (item.id == photo.id){
                list[index] = item.copy(isLike)
            }
        }
        mutableGalleryPhotoList.value = list
    }

}
