package com.example.photoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.photoapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_fragment.view.*


class ImageFragment : Fragment() {

    private val chosenImage = ImageDetailAcitvity.image

    companion object {
        fun newInstance(): ImageFragment {
            return ImageFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.image_fragment,container,false)
        Picasso.get().load(chosenImage.url).into(view.fragment_image)
        return view
    }

}