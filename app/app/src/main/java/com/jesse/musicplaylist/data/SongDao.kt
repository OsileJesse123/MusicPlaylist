package com.jesse.musicplaylist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface SongDao {

    @Insert
    suspend fun insertSong(song: Song)

    @Update
    suspend fun updateSong(song: Song)

    @Delete
    suspend fun deleteSong(song: Song)

    @Query("SELECT * FROM song WHERE :key = uid")
    suspend fun getSong(key: Int): Song?

    @Query("SELECT * FROM song WHERE song_artist LIKE :searchQuery OR song_name LIKE :searchQuery")
    fun searchSongs(searchQuery: String): LiveData<List<Song>>

    @Query("SELECT * FROM song ORDER BY uid DESC LIMIT 1")
    suspend fun getASong(): Song?

    @Query("SELECT * FROM song")
    fun getAllSongs(): LiveData<List<Song>>

}