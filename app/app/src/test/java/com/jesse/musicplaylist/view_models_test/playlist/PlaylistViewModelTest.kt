package com.jesse.musicplaylist.view_models_test.playlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jesse.musicplaylist.CoroutineDispatcherRule
import com.jesse.musicplaylist.data.Playlist
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.repository.SPRepository
import com.jesse.musicplaylist.screens.playlist.PlaylistViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.Rule
import org.mockito.kotlin.*
import java.util.*

class PlaylistViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val testDispatcherRule = CoroutineDispatcherRule()

    @ExperimentalCoroutinesApi
    @Test
    fun test_insertPlaylist(){
        runBlockingTest {
            //Arrange
            val playlists = mutableListOf<Playlist>()
            val playlist = Playlist(playlistName = "Hip Hop", addedOn = Calendar.getInstance().time)
            val playlistRepo: SPRepository<Playlist> = mock(){
                whenever(it.insertObject(any())).then {
                    playlists.add(playlist)
                }
            }
            val songRepo: SPRepository<Song> = mock()
            val playlistViewModel = PlaylistViewModel(playlistRepo, songRepo)
            val expectedSize = 1

            //Act
            playlistViewModel.insertPlaylist(playlist)

            //Assert
            verify(playlistRepo).insertObject(any())
            assertEquals(expectedSize, playlists.size)

        }
    }

    @Test
    fun test_deletePlaylist(){
        runBlockingTest {
            //Arrange
            val playlists = mutableListOf(Playlist(playlistName = "Hip Hop",
                addedOn = Calendar.getInstance().time))
            val playlistRepo: SPRepository<Playlist> = mock(){
                whenever(it.deleteObject(any())).then {
                    playlists.removeAt(0)
                }
            }
            val songRepo: SPRepository<Song> = mock()
            val playlistViewModel = PlaylistViewModel(playlistRepo, songRepo)

            //Act
            playlistViewModel.deletePlaylist(playlists[0])

            //Assert
            verify(playlistRepo).deleteObject(any())
            assertTrue(playlists.isEmpty())
        }
    }

    @Test
    fun test_getAllPlaylists(){
        //Arrange
        val playlistRepo: SPRepository<Playlist> = mock(){
            whenever(it.getAllObjects()).thenReturn(MutableLiveData(listOf()))
        }
        val songRepo: SPRepository<Song> = mock()
        val playlistViewModel = PlaylistViewModel(playlistRepo, songRepo)
        val expected = 0

        //Act
        val playlists = playlistViewModel.getAllPlaylists().value

        //Assert
        assertNotNull(playlists)
        assertEquals(expected, playlists?.size)
    }

    @Test
    fun test_updateSong(){
        runBlockingTest {
            //Arrange
            val song = Song(songArtist = "CKay", songName = "Felony")
            val playlistRepo: SPRepository<Playlist> = mock()
            val songRepo: SPRepository<Song> = mock(){
                whenever(it.updateObject(any())).doAnswer {
                    song.songId = 1
                }
            }
            val playlistViewModel = PlaylistViewModel(playlistRepo, songRepo)

            //Act
            playlistViewModel.updateSong(song)

            //Assert
            verify(songRepo).updateObject(argThat {
                song.songId == 1
            })
        }
    }
    @Test
    fun test_getAllSongs(){
        runBlockingTest {
            //Arrange
            val expected = 0
            val playlistRepo: SPRepository<Playlist> = mock()
            val songRepo: SPRepository<Song> = mock(){
                whenever(it.getAllObjects()).thenReturn(MutableLiveData(listOf()))
            }
            val playlistViewModel = PlaylistViewModel(playlistRepo, songRepo)

            //Act
            val songs = playlistViewModel.getAllSongs().value

            //Assert
            assertNotNull(songs)
            assertEquals(expected, songs?.size)
        }
    }

    @Test
    fun test_updatePlaylistSongsAfterDelete(){
        runBlockingTest{
            //Arrange
            val songs = listOf(
                Song(songArtist = "Burna Boy", songName = "Wetin dey sup", songStates = mutableListOf(1)),
                Song(songArtist = "CKay", songName = "Felony", songStates = mutableListOf(1)),
                Song(songArtist = "Davido", songName = "Fem", songStates = mutableListOf(1))
            )
            val playlistRepo: SPRepository<Playlist> = mock()
            val songRepo: SPRepository<Song> = mock()
            val playlistViewModel = PlaylistViewModel(playlistRepo, songRepo)

            //Act
            playlistViewModel.updatePlaylistSongsAfterDelete(songs, 1)

            //Assert
            verify(songRepo, times(songs.size)).updateObject(argThat {
                songStates!!.isEmpty()
            })
        }
    }

    @Test
    fun test_createPlaylist(){
        //Arrange
        val playlistRepo: SPRepository<Playlist> = mock()
        val songRepo: SPRepository<Song> = mock()
        val playlistViewModel = PlaylistViewModel(playlistRepo, songRepo)
        val playlistName = "Justin Vibe"

        //Act
        val playlist = playlistViewModel.createPlaylist(playlistName)

        //Assert
        assertEquals(playlistName, playlist.playlistName)
    }
}