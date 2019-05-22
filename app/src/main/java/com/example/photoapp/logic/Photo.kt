package com.example.photoapp.logic

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import java.io.Serializable
import java.util.*
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target;




class Photo(val url: String, val name:String,var tags: List<String>, val date:String) :Serializable{

  fun getTags(listTags: List <String>):String{

        return listTags.take(3).joinToString()

    }

}