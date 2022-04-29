package com.jesse.musicplaylist.screens.songs


import androidx.lifecycle.*
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.repository.SPRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongsViewModel @Inject constructor(): ViewModel(), LifecycleObserver {

    @Inject
    lateinit var songRepo: SPRepository<Song>

    fun deleteSong(song: Song){
        viewModelScope.launch {
            songRepo.deleteObject(song)
        }
    }

    fun getAllSongs(): LiveData<List<Song>>{
        return songRepo.getAllObjects()
    }

}