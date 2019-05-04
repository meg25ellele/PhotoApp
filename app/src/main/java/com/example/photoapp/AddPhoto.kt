package com.example.photoapp

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.photoapp.logic.Photo
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_add_photo.*
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photo_adapter.view.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class AddPhoto : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)


        add_button.setOnClickListener{addPhoto()}

    }

    fun addPhoto(){
        if(TextUtils.isEmpty(name_edit.text) or TextUtils.isEmpty(url_edit.text)){
            Toast.makeText(this@AddPhoto,getString(R.string.no_data),Toast.LENGTH_LONG).show()

            if (TextUtils.isEmpty(name_edit.text)) name_edit.error=getString(R.string.no_data)
            if (TextUtils.isEmpty(url_edit.text))  url_edit.error=getString(R.string.no_data)
        }
         else {
            val name =name_edit.text.toString()
            val url = url_edit.text.toString()

            val intent = Intent(this,MainActivity::class.java)

            val date = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US)
            val dateInString = date.format(Date())


            MainActivity.dataset.add(Photo(url,name, emptyList(),dateInString))

            startActivity(intent)



        }


    }

}
