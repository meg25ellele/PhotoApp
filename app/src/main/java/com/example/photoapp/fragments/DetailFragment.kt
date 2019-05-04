package com.example.photoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.photoapp.R
import kotlinx.android.synthetic.main.detail_fragment.view.*

class DetailFragment: Fragment(){

    private val chosenImage = ImageDetailAcitvity.image

    companion object {
        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.detail_fragment, container,false)
        view.detail_name.text = chosenImage.name
        view.detail_data.text = chosenImage.date
        view.detail_tags.text = chosenImage.getTags(chosenImage.tags)

        return view

    }




}