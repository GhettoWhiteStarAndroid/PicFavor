package com.ghettowhitestar.picfavor.presentation.like

import com.ghettowhitestar.picfavor.core.BasePhotoFragment
import com.ghettowhitestar.picfavor.databinding.FragmentLayoutBinding
import com.ghettowhitestar.picfavor.presentation.PhotoViewModel
import com.ghettowhitestar.picfavor.presentation.adapter.GalleryPhotoAdapter
import com.ghettowhitestar.picfavor.presentation.VisibilityStates
import com.ghettowhitestar.picfavor.utils.observe

class LikesFragment : BasePhotoFragment() {

    override fun FragmentLayoutBinding.initView() {
        adapter = GalleryPhotoAdapter{ photo, bitmap -> viewModel.changeLikePhoto(photo, bitmap) }

        recyclerView.adapter = adapter
    }

    override fun PhotoViewModel.observe() {
        observe(likedPhotoList) {
            it.let { items ->
                binding.layoutState(
                    if (it.isEmpty())
                        VisibilityStates.Empty
                    else
                        VisibilityStates.Visible
                )
                adapter.submitList(items)
            }
        }
    }
}