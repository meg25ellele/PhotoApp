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
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.photo_adapter.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

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



        if(dataset.isEmpty()) {
            createBasewithPhoto()
        }

        deleteIcon= ContextCompat.getDrawable(this,R.drawable.ic_delete)!!
        viewAdapter = Photo_Adapter(this@MainActivity,dataset)

        photo_recyclerView.layoutManager=LinearLayoutManager(this)
        photo_recyclerView.adapter= viewAdapter

        photo_recyclerView.setHasFixedSize(true)
        photo_recyclerView.itemAnimator = null


        if(dataset.isEmpty()) {
            createBasewithPhoto()
        }


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
        val intent = Intent(this@MainActivity,AddPhoto::class.java)
        startActivity(intent)

        return super.onOptionsItemSelected(item)

    }

    fun createBasewithPhoto(){

        val date = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US)
        val dateInString = date.format(Date())

        dataset.add(Photo("https://images.unsplash.com/photo-1518791841217-8f162f1e1131?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80","cat",emptyList(),dateInString))
        dataset.add((Photo("https://img.purch.com/w/660/aHR0cDovL3d3dy5saXZlc2NpZW5jZS5jb20vaW1hZ2VzL2kvMDAwLzEwNC84MTkvb3JpZ2luYWwvY3V0ZS1raXR0ZW4uanBn","kitten",
            emptyList(),dateInString)))
        dataset.add(Photo("https://m.salon24.pl/4bec06b82434aa4ae3b7f594d90ec392,750,0,0,0.jpg", "dogg", emptyList(),dateInString))
        dataset.add((Photo("https://www.petmd.com/sites/default/files/Acute-Dog-Diarrhea-47066074.jpg", "dog", emptyList(), dateInString)))
        dataset.add(Photo("https://i.wpimg.pl/985x0/m.fotoblogia.pl/kardynalek-jeremy-black-6cabc0d6.jpg","bird", emptyList(),dateInString))
        dataset.add(Photo("https://article.images.consumerreports.org/prod/content/dam/CRO%20Images%202019/Cars/January/CR-Cars-InlineHero-2019-Acura-ILX-A-Spec-f-1-19","car1",
            emptyList(),dateInString))
        dataset.add(Photo("https://img.newatlas.com/mazda-vision-coupe-wins-2018-concept-car-of-the-year-4.jpg?auto=format%2Ccompress&ch=Width%2CDPR&fit=crop&h=347&q=60&rect=0%2C106%2C1620%2C912&w=616&s=a40e38fdb6995f043b03589dd2e2bf6e","car2",
            emptyList(),dateInString))

        dataset.add(Photo("https://thumbor.forbes.com/thumbor/960x0/https%3A%2F%2Fspecials-images.forbesimg.com%2Fdam%2Fimageserve%2F1078084172%2F960x0.jpg%3Ffit%3Dscale", "girl", emptyList(),dateInString))

        dataset.add(Photo("https://dinoanimals.pl/wp-content/uploads/2012/09/Kangur-1.jpg","kangaroo", emptyList(),dateInString))
        dataset.add(Photo("http://www.fokarium.com/images/foka_szara_samica.jpg", "seal", emptyList(),dateInString))
        dataset.add(Photo("https://st3.depositphotos.com/8810948/19351/v/600/depositphotos_193510178-stock-video-bird-flying-and-eiffel-tower.jpg","tower", emptyList(),dateInString))
        dataset.add(Photo("https://cdn.someecards.com/posts/dumbest-things-people-believed-as-children-dGF.jpg", "boy", emptyList(),dateInString))
        dataset.add(Photo("https://s.hdnux.com/photos/74/35/31/15849063/3/920x920.jpg","plane", emptyList(),dateInString))
        dataset.add(Photo("https://whatnext.pl/wp-content/uploads/2019/03/defiant-maiden-2-1000x650.jpg","helicopter", emptyList(),dateInString))
        dataset.add(Photo("https://files.salsacdn.com/service/378-SVBAU/image/7-z_d_0_0_800.20190507145630.jpg", "boat", emptyList(),dateInString))
        dataset.add(Photo("https://ocs-pl.oktawave.com/v1/AUTH_2887234e-384a-4873-8bc5-405211db13a2/splay/2019/05/gra-o-tron-jon-snow-co-dalej-1180x588.jpg","snow", emptyList(),dateInString))
        dataset.add(Photo("https://img.purch.com/w/660/aHR0cDovL3d3dy5saXZlc2NpZW5jZS5jb20vaW1hZ2VzL2kvMDAwLzA2NC81MTQvb3JpZ2luYWwvZ290LWRyYWdvbnMuanBn", "dragon", emptyList(),dateInString))



    }

}
