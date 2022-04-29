package com.jesse.musicplaylist.screens.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.repository.SPRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var songRepo: SPRepository<Song>

    fun searchSongs(searchQuery: String): LiveData<List<Song>>{
        return songRepo.searchObjects(searchQuery)
    }

}