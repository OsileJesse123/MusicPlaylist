package com.jesse.musicplaylist.screens.playlist


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.musicplaylist.data.Playlist
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.repository.SPRepository
import kotlinx.coroutines.launch

import java.util.*

class PlaylistViewModel(private val playlistRepo: SPRepository<Playlist>,
                        private val songRepo: SPRepository<Song>): ViewModel() {

    fun insertPlaylist(playlist: Playlist){
        viewModelScope.launch {
            playlistRepo.insertObject(playlist)
        }
    }

    fun deletePlaylist(playlist: Playlist){
        viewModelScope.launch {
            playlistRepo.deleteObject(playlist)
        }
    }

    fun getAllPlaylists(): LiveData<List<Playlist>> {
        return playlistRepo.getAllObjects()
    }

    fun updateSong(song: Song){
        viewModelScope.launch {
            songRepo.updateObject(song)
        }
    }

    fun getAllSongs(): LiveData<List<Song>>{
        return songRepo.getAllObjects()
    }

    fun updatePlaylistSongsAfterDelete(allSongs: List<Song>, uid: Int) {
        if (allSongs.isNotEmpty()){
            for (song in allSongs){
                song.songStates!!.remove(uid)
                updateSong(song)
            }
        }
    }

    fun createPlaylist(playlistName: String): Playlist {
        val date = Calendar.getInstance().time
        return Playlist(playlistName = playlistName, addedOn = date)
    }

}