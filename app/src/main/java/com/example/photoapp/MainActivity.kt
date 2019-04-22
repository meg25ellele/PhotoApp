package com.example.photoapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photoapp.logic.Photo
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.image_adapter.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    companion object {

        var dataset = mutableListOf<Photo>()
        private lateinit var viewAdapter: RecyclerView.Adapter<*>
        private lateinit var deleteIcon: Drawable
        private var swipeBackground: ColorDrawable = ColorDrawable(Color.parseColor("#FF0000"))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        deleteIcon= ContextCompat.getDrawable(this,R.drawable.ic_delete)!!
        viewAdapter = Photo_Adapter(dataset)

        photo_recyclerView.layoutManager=LinearLayoutManager(this)
        photo_recyclerView.adapter= viewAdapter


        photo_recyclerView.itemAnimator = null

        val itemTouchHelperCallback = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - deleteIcon.intrinsicHeight)/2
                if(dX>0){
                    swipeBackground.setBounds(itemView.left,itemView.top,dX.toInt(),itemView.bottom)
                    deleteIcon.setBounds(itemView.left + iconMargin,itemView.top + iconMargin, itemView.left + iconMargin + deleteIcon.intrinsicWidth, itemView.bottom-iconMargin)
                } else {
                    swipeBackground.setBounds(itemView.right+dX.toInt(),itemView.top,itemView.right,itemView.bottom)
                    deleteIcon.setBounds(itemView.right - iconMargin - deleteIcon.intrinsicWidth,itemView.top + iconMargin, itemView.right - iconMargin, itemView.bottom-iconMargin)

                }
                swipeBackground.draw(c)

                c.save()

                if(dX>0)
                    c.clipRect(itemView.left,itemView.top,dX.toInt(),itemView.bottom)
                else
                    c.clipRect(itemView.right+dX.toInt(),itemView.top,itemView.right,itemView.bottom)

                deleteIcon.draw(c)

                c.restore()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                (viewAdapter as Photo_Adapter).removeItem(viewHolder)
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(photo_recyclerView)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val intent = Intent(this,AddPhoto::class.java)
        startActivity(intent)

        return super.onOptionsItemSelected(item)

    }

}
