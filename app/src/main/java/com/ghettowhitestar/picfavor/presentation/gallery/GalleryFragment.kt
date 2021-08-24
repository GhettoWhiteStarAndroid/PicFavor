package com.ghettowhitestar.picfavor.presentation.gallery

import android.view.View
import com.ghettowhitestar.picfavor.R
import com.ghettowhitestar.picfavor.core.BasePhotoFragment
import com.ghettowhitestar.picfavor.databinding.FragmentLayoutBinding
import com.ghettowhitestar.picfavor.presentation.PhotoViewModel
import com.ghettowhitestar.picfavor.presentation.VisibilityStates
import com.ghettowhitestar.picfavor.presentation.paginator.PaginationListener
import com.ghettowhitestar.picfavor.utils.observe
import com.ghettowhitestar.picfavor.utils.toast
import dagger.hilt.android.AndroidEntryPoint

/** Фрагмент отвечающий за отображение случайных фотографий */
@AndroidEntryPoint
class GalleryFragment : BasePhotoFragment() {

    override fun FragmentLayoutBinding.initView() {
        progressBar.visibility = View.VISIBLE
        textViewError.text = getString(R.string.connectionInternet)
        recyclerView.addOnScrollListener(PaginationListener(viewModel))

        buttonRetry.setOnClickListener {
            layoutState(VisibilityStates.Loading)
            viewModel.checkNetwork()
        }
    }

    override fun PhotoViewModel.observe() {
        observe(galleryPhotoList) {
            it.let { items ->
                adapter.updateItems(items)
                binding.layoutState(VisibilityStates.Visible)
            }
        }
        observe(isStartNetwork) {
            it.let { isAvailable ->
                binding.layoutState(
                    if (isAvailable)
                        VisibilityStates.Visible
                    else
                        VisibilityStates.Retry
                )
            }
        }
        observe(toastMessage) {
            it.let { message ->
                context?.toast(message)
            }
        }
    }
}