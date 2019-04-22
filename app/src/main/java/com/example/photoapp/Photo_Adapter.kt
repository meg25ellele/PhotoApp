package com.example.photoapp

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoapp.logic.Photo
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_photo.view.*
import kotlinx.android.synthetic.main.image_adapter.view.*

class Photo_Adapter (private val dataset: MutableList<Photo>): RecyclerView.Adapter<CustomViewHolder>(){



    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val layoutInflater= LayoutInflater.from(parent.context)
        val cellForRow  = layoutInflater.inflate(R.layout.image_adapter,parent,false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        Picasso.get().load(dataset[position].url).into(holder.view.photo)
        holder.view.name.text = dataset[position].name
        holder.view.date.text = dataset[position].date
        holder.view.tags.text = dataset[position].tags

    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder){
        dataset.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)
    }



}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}
