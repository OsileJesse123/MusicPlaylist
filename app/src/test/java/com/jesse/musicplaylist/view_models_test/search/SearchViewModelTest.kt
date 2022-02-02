package com.jesse.musicplaylist.view_models_test.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.repository.SPRepository
import com.jesse.musicplaylist.screens.search.SearchViewModel
import com.jraska.livedata.test
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun test_searchSongs(){
        //Arrange
        val searchQuery = "CKay"
        val listOfSongs = mutableListOf(
            Song(songArtist = "CKay", songName = "Felony"),
            Song(songArtist = "Burna", songName = "Burn"),
            Song(songArtist = "Fire", songName = "Fir")
        )
        val expectedList = listOfSongs.filter {
            it.songArtist == searchQuery
        }
        val songRepo: SPRepository<Song> = mock(){ sr ->
            whenever(sr.searchObjects(any())).thenReturn(
                MutableLiveData(expectedList)
            )
        }
        val searchViewModel = SearchViewModel(songRepo)

        //Act
        val actualList = searchViewModel.searchSongs(searchQuery).test().value()

        //Assert
        verify(songRepo).searchObjects(any())
        assertEquals(expectedList, actualList)
    }
}