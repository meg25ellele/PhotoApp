package com.example.photoapp

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoapp.logic.Photo
import com.google.android.gms.common.data.DataHolder
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_photo.view.*
import kotlinx.android.synthetic.main.image_adapter.view.*
import androidx.recyclerview.widget.SimpleItemAnimator



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



        Picasso.get().load(dataset[position].url).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                processImageTagging(bitmap,position)
                holder.view.photo.setImageBitmap(bitmap)


            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
        })



        holder.view.tags.text = getTags(dataset[position].tags)
        holder.view.name.text = dataset[position].name
        holder.view.date.text = dataset[position].date

    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder){
        dataset.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)
    }

    private fun processImageTagging(bitmap: Bitmap?,position: Int) {
        val visionImg = FirebaseVisionImage.fromBitmap(bitmap!!)
        val labeler = FirebaseVision.getInstance().onDeviceImageLabeler
        labeler.processImage(visionImg)
            .addOnSuccessListener { tags ->
                val stringTags= tags.joinToString(" ") { it.text }
                dataset[position].tags = stringTags.split(" ")
                notifyItemChanged(position,null)

            }
            .addOnFailureListener { ex ->
                Log.wtf("LAB", ex)
            }
    }

    private fun getTags(listTags: List <String>):String{

        return listTags.take(3).joinToString()



    }


}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}
