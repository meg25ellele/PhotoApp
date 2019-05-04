package com.example.photoapp.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.photoapp.R
import com.example.photoapp.logic.Photo
import kotlinx.android.synthetic.main.activity_image_detail.*

class ImageDetailAcitvity : AppCompatActivity() {

    companion object {
        lateinit var image: Photo
        val IMAGE = "IMAGE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        image = intent.getSerializableExtra(IMAGE) as Photo

        val pagerAdapter = ImageFragmentPagerAdapter(supportFragmentManager)
        viewPager.adapter=pagerAdapter

    }
}
