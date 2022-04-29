package com.jesse.musicplaylist.screens.add_song


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.repository.SPRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSongViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var songRepo: SPRepository<Song>

    fun updateSong(song: Song){
        viewModelScope.launch {
            songRepo.updateObject(song)
        }
    }

    fun getAllSongs(): LiveData<List<Song>> {
        return songRepo.getAllObjects()
    }

    fun updateSongStates(playlistId: Int, song: Song) {
        if(song.songStates == null) {
            song.songStates = mutableListOf()
            song.songStates!!.add(playlistId)
        } else {
            song.songStates!!.add(playlistId)
        }
        updateSong(song)
    }
}