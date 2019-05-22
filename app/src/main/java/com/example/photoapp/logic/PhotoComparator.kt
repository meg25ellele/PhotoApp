package com.example.photoapp.logic

import com.example.photoapp.MainActivity
import java.io.Serializable

class PhotoComparator (val photo: Photo, val possition:Int) :Serializable {

    companion object {
        private var photobase = MainActivity.dataset
        private var successList= mutableMapOf<Int,Int>() //1-index, 2-success



    }

    fun compare ():Map<Int,Int>{

        val photoTags = photo.tags

        for(i in 0 until photobase.size){
            if(i!=possition) {
                successList[i] = compareSingle(photoTags, photobase[i].tags)
            } else successList[i] = 0
        }

        return sortBySuccess(successList)


    }


    private fun compareSingle(tags1:List<String>?,tags2: List <String>?):Int{
        var success = 0

        tags1?.forEach { tag1 ->
            tags2?.forEach { tag2 ->
                if (tag1 == tag2) {
                    success++
                }
            }
        }
        return success

    }
    
    private fun sortBySuccess(toSort:MutableMap<Int,Int>):Map<Int,Int>{
        for(i in 0 until  toSort.size){
            if(toSort[i]==0) toSort.remove(i)
        }
        return toSort.toList().sortedBy { (_,value) -> value }.toMap()

    }
}