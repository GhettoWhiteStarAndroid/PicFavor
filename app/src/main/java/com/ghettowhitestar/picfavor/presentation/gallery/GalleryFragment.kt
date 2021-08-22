package com.ghettowhitestar.picfavor.presentation.gallery

import android.view.View
import androidx.fragment.app.activityViewModels
import com.ghettowhitestar.picfavor.R
import com.ghettowhitestar.picfavor.core.BaseFragment
import com.ghettowhitestar.picfavor.databinding.FragmentLayoutBinding
import com.ghettowhitestar.picfavor.presentation.PhotoViewModel
import com.ghettowhitestar.picfavor.presentation.VisibilityStates
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
            layoutState(VisibilityStates.Loading)
            /*viewModel.checkNetworkConnection()*/
        }

        viewModel.galleryPhotoList.observe(viewLifecycleOwner, {
            it.let { items ->
                adapter.updateItems(items)
                progressBar.visibility = View.GONE
            }
        })

        viewModel.isStartNetwork.observe(viewLifecycleOwner, {
            layoutState(if(it) VisibilityStates.Retry else VisibilityStates.Visible)
        })
    }
}