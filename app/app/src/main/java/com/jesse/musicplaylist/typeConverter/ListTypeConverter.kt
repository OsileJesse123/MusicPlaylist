package com.jesse.musicplaylist.typeConverter

import androidx.room.TypeConverter

class ListTypeConverter {

    @TypeConverter
    fun toList(string: String?): MutableList<Int>?{
        var number = ""
        val listOfNumbers = mutableListOf<Int>()
        string?.let {
            for(i in it){
                if (i != ','){
                    number+=i
                } else {
                    listOfNumbers.add(number.toInt())
                    number = ""
                }
            }
        }
        return if(string == null) null else listOfNumbers
    }

    @TypeConverter
    fun toStrings(list: MutableList<Int>?) : String? {
        var word = ""
        list?.let {
            for(i in it){
                word+= "$i,"
            }
        }
        return if (list == null) null else word
    }
}