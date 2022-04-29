package com.jesse.musicplaylist.typeConverter

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {

    @TypeConverter
    fun toDate(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun toLong(value: Date): Long {
        return value.time
    }
}