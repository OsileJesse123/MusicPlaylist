package com.jesse.musicplaylist.screens.playlist_songs


import androidx.lifecycle.*
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.repository.SPRepository
import com.jesse.musicplaylist.repository.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistSongsViewModel @Inject constructor(): ViewModel(){

    @Inject
    lateinit var songRepo: SPRepository<Song>

    private val songs_: MutableLiveData<List<Song>> = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> get() = songs_

    fun setSongs(songsList: List<Song>){
        songs_.value = songsList
    }

    fun deleteSong(song: Song){
        viewModelScope.launch {
            songRepo.deleteObject(song)
        }
    }

    fun getAllSongs(): LiveData<List<Song>>{
        return songRepo.getAllObjects()
    }
}