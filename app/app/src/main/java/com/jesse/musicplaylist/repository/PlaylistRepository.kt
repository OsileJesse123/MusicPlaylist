package com.jesse.musicplaylist.repository


import androidx.lifecycle.LiveData
import com.jesse.musicplaylist.data.Playlist
import com.jesse.musicplaylist.data.PlaylistDao
import javax.inject.Inject

class PlaylistRepository @Inject constructor(private val playlistDao: PlaylistDao):
    SPRepository<Playlist> {

    override suspend fun insertObject(obj: Playlist) {
        playlistDao.insertPlaylist(obj)
    }

    override suspend fun deleteObject(obj: Playlist) {
        playlistDao.deletePlaylist(obj)
    }

    override suspend fun getObject(index: Int): Playlist? {
        return playlistDao.getPlaylist(index)
    }

    override fun getAllObjects(): LiveData<List<Playlist>> {
        return playlistDao.getAllPlaylists()
    }

    override suspend fun updateObject(obj: Playlist) {
        //The Playlist Repository Does not require this function
    }

    override fun searchObjects(searchQuery: String): LiveData<List<Playlist>> {
        TODO("Not yet implemented")
        //The Playlist Repository Does not require this function
    }
}