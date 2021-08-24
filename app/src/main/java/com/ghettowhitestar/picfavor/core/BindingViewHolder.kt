package com.ghettowhitestar.picfavor.core

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BindingViewHolder<T : ViewBinding>(
    protected val binding: T
) : RecyclerView.ViewHolder(binding.root)