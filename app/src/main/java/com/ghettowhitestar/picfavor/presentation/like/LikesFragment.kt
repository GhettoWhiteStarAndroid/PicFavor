package com.ghettowhitestar.picfavor.presentation.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ghettowhitestar.picfavor.R
import com.ghettowhitestar.picfavor.core.BaseFragment
import com.ghettowhitestar.picfavor.databinding.FragmentLayoutBinding
import com.ghettowhitestar.picfavor.presentation.adapter.GalleryPhotoAdapter
import com.ghettowhitestar.picfavor.presentation.PhotoViewModel

class LikesFragment : BaseFragment() {

    private val viewModel: PhotoViewModel by activityViewModels()
    private lateinit var adapter: GalleryPhotoAdapter

    override fun FragmentLayoutBinding.initView() {
        adapter = GalleryPhotoAdapter { photo, bitmap -> viewModel.changeLikePhoto(photo, bitmap) }

        binding.apply {
            recyclerView.adapter = adapter
            buttonRetry.setOnClickListener { }
        }
        viewModel.likedPhotoList.observe(viewLifecycleOwner, {
            it.let { items ->
                isLikeListEmpty(it.isEmpty())
                adapter.updateItems(items)
            }
        })
    }

    private fun FragmentLayoutBinding.isLikeListEmpty(isEmpty: Boolean) {
        if (isEmpty) {
            textViewError.visibility = View.VISIBLE
        } else {
            textViewError.visibility = View.GONE
        }
    }


}