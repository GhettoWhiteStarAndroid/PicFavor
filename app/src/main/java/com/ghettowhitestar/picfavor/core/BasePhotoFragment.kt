package com.ghettowhitestar.picfavor.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ghettowhitestar.picfavor.databinding.FragmentLayoutBinding
import com.ghettowhitestar.picfavor.presentation.PhotoViewModel
import com.ghettowhitestar.picfavor.presentation.VisibilityStates
import com.ghettowhitestar.picfavor.presentation.adapter.GalleryPhotoAdapter

abstract class BasePhotoFragment : Fragment() {

    protected lateinit var binding: FragmentLayoutBinding
    protected val viewModel: PhotoViewModel by activityViewModels()
    protected lateinit var adapter: GalleryPhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    abstract fun FragmentLayoutBinding.initView()

    abstract fun PhotoViewModel.observe()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initView()
        viewModel.observe()
    }

    fun FragmentLayoutBinding.layoutState(state: VisibilityStates) {
        when (state){
            VisibilityStates.Loading -> {
                progressBar.visibility = View.VISIBLE
                textViewError.visibility = View.GONE
                buttonRetry.visibility = View.GONE
            }
            VisibilityStates.Retry -> {
                progressBar.visibility = View.GONE
                textViewError.visibility = View.VISIBLE
                buttonRetry.visibility = View.VISIBLE
            }
            VisibilityStates.Visible -> {
                textViewError.visibility = View.GONE
                buttonRetry.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
            VisibilityStates.Empty -> {
                textViewError.visibility = View.VISIBLE
            }
        }
    }
}