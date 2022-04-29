package com.jesse.musicplaylist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlaylistDao {

    @Insert
    suspend fun insertPlaylist(playlist: Playlist)

    @Delete
    suspend fun deletePlaylist(playlist: Playlist)

    @Query("SELECT * FROM playlist WHERE :key = uid")
    suspend fun getPlaylist(key: Int): Playlist?

    @Query("SELECT * FROM playlist")
    fun getAllPlaylists(): LiveData<List<Playlist>>

    @Transaction
    @Query("SELECT * FROM playlist")
    fun getPlaylistWithSongs(): LiveData<List<PlaylistWithSongs>>
}