package com.example.photoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.photoapp.MainActivity
import com.example.photoapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_fragment.view.*
import kotlinx.android.synthetic.main.gallery6_fragment.view.*


class Gallery6Fragment : Fragment () {

    val successList = ImageDetailAcitvity.success
   val dataset = MainActivity.dataset

    companion object {
        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.gallery6_fragment, container, false)

        if (getSimilar(1)!="") {
            Picasso.get().load(dataset[successList[successList.size-1]].url).into(view.image1)
        }
        if (getSimilar(2)!="") {
            Picasso.get().load(dataset[successList[successList.size-2]].url).into(view.image2)
        }
        if (getSimilar(3)!="") {
            Picasso.get().load(dataset[successList[successList.size-3]].url).into(view.image3)
        }
        if (getSimilar(4)!="") {
            Picasso.get().load(dataset[successList[successList.size-4]].url).into(view.image4)
        }
        if (getSimilar(5)!="") {
            Picasso.get().load(dataset[successList[successList.size-5]].url).into(view.image5)
        }
        if (getSimilar(6)!="") {
            Picasso.get().load(dataset[successList[successList.size-6]].url).into(view.image6)
        }

        return view

    }

    private fun getSimilar(n: Int) : String{
        return if(successList.size-n > 0){
            dataset[successList[successList.size-n]].url
        } else ""
    }
}

