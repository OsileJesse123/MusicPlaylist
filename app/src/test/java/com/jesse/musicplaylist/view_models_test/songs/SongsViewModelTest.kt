package com.jesse.musicplaylist.view_models_test.songs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jesse.musicplaylist.CoroutineDispatcherRule
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.repository.SPRepository
import com.jesse.musicplaylist.screens.songs.SongsViewModel
import com.jraska.livedata.test
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SongsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineDispatcherRule = CoroutineDispatcherRule()

    private lateinit var listOfSongs: MutableList<Song>

    @Before
    fun setup(){
        listOfSongs = mutableListOf(
            Song(songArtist = "CKay", songName = "Felony"),
            Song(songArtist = "Burna", songName = "Burn"),
            Song(songArtist = "Fire", songName = "Fir")
        )
    }

    @Test
    fun test_deleteSong(){
        runBlockingTest{
            //Arrange
            val position = 0
            val deletedSong = Song(songArtist = "CKay", songName = "Felony")
            val songRepo: SPRepository<Song> = mock(){
                whenever(it.deleteObject(any())).then {
                    listOfSongs.removeAt(position)
                }
            }
            val songsViewModel = SongsViewModel(songRepo)

            //Act
            songsViewModel.deleteSong(listOfSongs[position])

            //Assert
            verify((songRepo)).deleteObject(any())
            Assert.assertFalse(listOfSongs.contains(deletedSong))
        }
    }

    @Test
    fun test_getAllSongs(){
        //Arrange
        val songRepo: SPRepository<Song> = mock(){
            whenever(it.getAllObjects()).thenReturn(MutableLiveData(listOfSongs))
        }
        val songsViewModel = SongsViewModel(songRepo)

        //Act
        val actualListOfSongs = songsViewModel.getAllSongs().test().value()

        //Assert
        verify(songRepo).getAllObjects()
        Assert.assertEquals(listOfSongs, actualListOfSongs)
    }
}