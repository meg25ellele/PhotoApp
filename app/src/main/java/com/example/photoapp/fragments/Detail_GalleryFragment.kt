package com.example.photoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.photoapp.R


class Detail_GalleryFragment: Fragment() {

    companion object {
        fun newInstance(): Detail_GalleryFragment {
            return Detail_GalleryFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_gallery_fragment,container, false)
    }
}