package com.jesse.musicplaylist.screens.songs


import androidx.lifecycle.*
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.repository.SPRepository

import kotlinx.coroutines.launch

class SongsViewModel(private val songRepo: SPRepository<Song>): ViewModel(), LifecycleObserver {

    fun deleteSong(song: Song){
        viewModelScope.launch {
            songRepo.deleteObject(song)
        }
    }

    fun getAllSongs(): LiveData<List<Song>>{
        return songRepo.getAllObjects()
    }

}