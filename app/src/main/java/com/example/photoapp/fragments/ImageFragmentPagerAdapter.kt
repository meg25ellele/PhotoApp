package com.example.photoapp.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ImageFragmentPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ImageFragment.newInstance()
            1 -> Detail_GalleryFragment.newInstance()
            else -> ImageFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 2
    }

}