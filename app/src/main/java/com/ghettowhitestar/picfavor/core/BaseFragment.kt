package com.ghettowhitestar.picfavor.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ghettowhitestar.picfavor.databinding.FragmentLayoutBinding
import com.ghettowhitestar.picfavor.presentation.VisibilityStates

abstract class BaseFragment : Fragment() {

    protected lateinit var binding: FragmentLayoutBinding
    abstract fun FragmentLayoutBinding.initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initView()
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
            }
            VisibilityStates.Empty -> {
                textViewError.visibility = View.VISIBLE
            }
        }
    }
}