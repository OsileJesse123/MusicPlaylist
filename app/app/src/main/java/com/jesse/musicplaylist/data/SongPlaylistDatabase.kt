package com.jesse.musicplaylist.data

import android.content.Context
import androidx.room.*
import com.jesse.musicplaylist.migrations.MIGRATIONS
import com.jesse.musicplaylist.typeConverter.DateTypeConverter
import com.jesse.musicplaylist.typeConverter.ListTypeConverter


@Database(entities = [Song::class, Playlist::class], version = 2 , exportSchema = true)
@TypeConverters(DateTypeConverter::class, ListTypeConverter::class)
abstract class SongPlaylistDatabase: RoomDatabase() {

    abstract fun songDao(): SongDao
    abstract fun playlistDao(): PlaylistDao

}