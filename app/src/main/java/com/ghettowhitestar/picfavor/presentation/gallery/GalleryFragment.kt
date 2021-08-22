package com.ghettowhitestar.picfavor.presentation.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ghettowhitestar.picfavor.R
import com.ghettowhitestar.picfavor.core.BaseFragment
import com.ghettowhitestar.picfavor.databinding.FragmentLayoutBinding
import com.ghettowhitestar.picfavor.presentation.PhotoViewModel
import com.ghettowhitestar.picfavor.presentation.adapter.GalleryPhotoAdapter
import com.ghettowhitestar.picfavor.presentation.paginator.PaginationListener
import dagger.hilt.android.AndroidEntryPoint

/** Фрагмент отвечающий за отображение случайных фотографий */
@AndroidEntryPoint
class GalleryFragment : BaseFragment() {

    private val viewModel: PhotoViewModel by activityViewModels()

    private lateinit var adapter: GalleryPhotoAdapter

    override fun FragmentLayoutBinding.initView() {
        adapter = GalleryPhotoAdapter { photo, bitmap -> viewModel.changeLikePhoto(photo, bitmap) }

        progressBar.visibility = View.VISIBLE
        textViewError.text = getString(R.string.connectionInternet)
        recyclerView.addOnScrollListener(PaginationListener(viewModel))
        recyclerView.adapter = adapter
        buttonRetry.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            textViewError.visibility = View.GONE
            buttonRetry.visibility = View.GONE
            /*viewModel.checkNetworkConnection()*/
        }

        viewModel.galleryPhotoList.observe(viewLifecycleOwner, {
            it.let { items ->
                adapter.updateItems(items)
                progressBar.visibility = View.GONE
            }
        })

        viewModel.isStartNetwork.observe(viewLifecycleOwner, {
            isGalleryEmpty(it)
        })
    }

    private fun FragmentLayoutBinding.isGalleryEmpty(isNetwork: Boolean) {
        if (isNetwork) {
            progressBar.visibility = View.GONE
            textViewError.visibility = View.VISIBLE
            buttonRetry.visibility = View.VISIBLE
        } else {
            textViewError.visibility = View.GONE
            buttonRetry.visibility = View.GONE
        }
    }
}