package com.example.photoapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoapp.logic.Photo
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photo_adapter.view.*
import com.example.photoapp.fragments.ImageDetailAcitvity
import com.example.photoapp.logic.PhotoComparator
import com.google.android.gms.common.data.DataHolder
import com.squareup.picasso.Target


class Photo_Adapter (val context: Context, private val dataset: MutableList<Photo>): RecyclerView.Adapter<CustomViewHolder>(){



    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {


        val layoutInflater= LayoutInflater.from(parent.context)
        val cellForRow  = layoutInflater.inflate(R.layout.photo_adapter,parent,false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val target: Target = MyTarget(holder, position)
        Picasso.get().load(dataset[position].url).into(target)



        holder.view.photo.setOnClickListener{
            run {
                val intent = Intent(context, ImageDetailAcitvity::class.java)
                intent.putExtra(ImageDetailAcitvity.IMAGE,dataset[position])

                val successMap = PhotoComparator(dataset[position],position).compare().keys.toIntArray()
                intent.putExtra(ImageDetailAcitvity.SUCCESS,successMap)

                context.startActivity(intent)
            }
        }

        holder.view.name.text = dataset[position].name
        holder.view.date.text = dataset[position].date
        holder.view.tags.text = dataset[position].getTags(dataset[position].tags)

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
                notifyItemChanged(position)
            }
            .addOnFailureListener { ex ->
                Log.wtf("LAB", ex)
            }
    }

    inner class MyTarget(private val holder: CustomViewHolder, private val position: Int) : Target {
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            holder.view.photo.setImageBitmap(bitmap)
            processImageTagging(bitmap, position)
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            //holder.pic.setImageResource(R.mipmap.merc)
        }

    }



}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}
