package com.jesse.musicplaylist.screens.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.repository.SPRepository



class SearchViewModel(private val songRepo: SPRepository<Song>): ViewModel() {

    fun searchSongs(searchQuery: String): LiveData<List<Song>>{
        return songRepo.searchObjects(searchQuery)
    }

}