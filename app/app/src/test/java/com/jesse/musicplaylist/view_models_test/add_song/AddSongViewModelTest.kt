package com.jesse.musicplaylist.view_models_test.add_song

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jesse.musicplaylist.CoroutineDispatcherRule
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.repository.SPRepository
import com.jesse.musicplaylist.screens.add_song.AddSongViewModel
import com.jraska.livedata.test
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*


class AddSongViewModelTest {


    @get:Rule
    val instantTestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineDispatcherRule = CoroutineDispatcherRule()


    @Test
    fun test_updateSong(){
       runBlockingTest {
           val song = Song(songArtist = "Kanye", songName = "Ye")
           val expectedSongId = 1

           val songRepo: SPRepository<Song> = mock(){
               whenever(it.updateObject(any())).doAnswer {
                   song.songId = 1
               }
           }

           AddSongViewModel(songRepo).updateSong(song)

           verify(songRepo).updateObject(argThat {
               songId == expectedSongId
           })
       }
    }

    @Test
    fun test_getAllSongsEmpty(){

            val expectedListSize = 0
            val songRepo: SPRepository<Song> = mock(){
                whenever(it.getAllObjects()).thenReturn(MutableLiveData(listOf()))
            }
            val addSongViewModel = AddSongViewModel(songRepo)

            val actualListSize = addSongViewModel.getAllSongs().test().value().size

            assertEquals(expectedListSize, actualListSize)
    }

    @Test
    fun test_getAllSongsNotEmpty(){
        val expectedListSize = 3
        val listOfSongs = listOf(
            Song(songName = "Felony", songArtist = "CKay"),
            Song(songName = "Wetin Dey Sup", songArtist = "Burna Boy"),
            Song(songName = "Dumebi", songArtist = "Rema")
        )
        val songRepo: SPRepository<Song> = mock(){
            whenever(it.getAllObjects()).thenReturn(MutableLiveData(listOfSongs))
        }
        val addSongViewModel = AddSongViewModel(songRepo)

        val actualListSize = addSongViewModel.getAllSongs().test().value().size

        assertEquals(expectedListSize, actualListSize)
    }

    @Test
    fun test_updateSongStatesNull(){
        runBlockingTest {
            val playlistId = 1
            val song =  Song(songName = "Felony", songArtist = "CKay")
            val songRepo: SPRepository<Song> = mock()
            val addSongViewModel = AddSongViewModel(songRepo)
            val expectedSize = 1

            addSongViewModel.updateSongStates(playlistId, song)

            verify(songRepo).updateObject(argThat {
                songStates!!.size == expectedSize
            })

        }
    }

    @Test
    fun test_updateSongStatesNotNull(){
        runBlockingTest {
            val playlistId = 3
            val song = Song(songName = "Felony", songArtist = "CKay", songStates = mutableListOf(1,2))
            val songRepo: SPRepository<Song> = mock()
            val addSongViewModel = AddSongViewModel(songRepo)
            val expectedSize = 3

            addSongViewModel.updateSongStates(playlistId, song)

            verify(songRepo).updateObject(argThat {
                songStates!!.size == expectedSize
            })
        }
    }

}