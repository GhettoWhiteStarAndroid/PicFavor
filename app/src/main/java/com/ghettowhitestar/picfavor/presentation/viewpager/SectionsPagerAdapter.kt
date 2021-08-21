package com.ghettowhitestar.picfavor.presentation.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ghettowhitestar.picfavor.presentation.gallery.GalleryFragment
import com.ghettowhitestar.picfavor.presentation.like.LikesFragment
import java.lang.IllegalStateException


class SectionsPagerAdapter(fm: FragmentActivity)
    : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position){
            0-> GalleryFragment()
            1-> LikesFragment()
            else -> throw IllegalStateException("Fragment $position is not correct")
        }
}