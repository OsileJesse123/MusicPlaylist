package com.jesse.musicplaylist.repository


import androidx.lifecycle.LiveData
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.data.SongDao

class SongRepository(private val songDao: SongDao): SPRepository<Song> {

    override suspend fun insertObject(obj: Song) {
        songDao.insertSong(obj)
    }

    override suspend fun deleteObject(obj: Song) {
        songDao.deleteSong(obj)
    }

    //This function is mainly for test purposes
    override suspend fun getObject(index: Int): Song? {
        return songDao.getSong(index)
    }

    override fun getAllObjects(): LiveData<List<Song>> {
       return songDao.getAllSongs()
    }

    override suspend fun updateObject(obj: Song) {
        songDao.updateSong(obj)
    }

    override fun searchObjects(searchQuery: String): LiveData<List<Song>>{
        return songDao.searchSongs(searchQuery)
    }
}