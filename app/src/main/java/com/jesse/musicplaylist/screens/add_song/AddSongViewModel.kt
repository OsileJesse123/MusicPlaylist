package com.jesse.musicplaylist.screens.add_song


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.repository.SPRepository
import kotlinx.coroutines.launch

class AddSongViewModel(private val songRepo: SPRepository<Song>): ViewModel() {

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