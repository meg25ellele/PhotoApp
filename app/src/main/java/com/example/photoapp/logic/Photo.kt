package com.example.photoapp.logic

import java.io.Serializable
import java.util.*

class Photo(val url: String, val name:String,var tags: List<String>, val date:String) :Serializable{

  fun getTags(listTags: List <String>):String{

        return listTags.take(3).joinToString()



    }
}