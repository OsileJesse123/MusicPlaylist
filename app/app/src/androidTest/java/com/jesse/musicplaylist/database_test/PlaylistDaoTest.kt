package com.jesse.musicplaylist.database_test

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jesse.musicplaylist.blockingObserve
import com.jesse.musicplaylist.data.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class PlaylistDaoTest {

    @get: Rule
    val instantTask = InstantTaskExecutorRule()

    private lateinit var songPlaylistDb: SongPlaylistDatabase
    private lateinit var playlistDao: PlaylistDao
    private val playlists = listOf(
        Playlist(playlistName = "Rap", addedOn = Calendar.getInstance().time),
        Playlist(playlistName = "HipHop", addedOn = Calendar.getInstance().time),
        Playlist(playlistName = "RnB", addedOn = Calendar.getInstance().time)
    )
    private lateinit var songDao: SongDao
    private val listOfSongs = listOf(
        Song(songName = "Black Skin Head", songArtist = "Kanye West"),
        Song(songName = "That Way", songArtist = "Lil Uzi Vert"),
        Song(songName = "Felony", songArtist = "CKay")
    )


    @Before
    fun setUp(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        songPlaylistDb = Room.inMemoryDatabaseBuilder(context, SongPlaylistDatabase::class.java)
            .fallbackToDestructiveMigration()
            .build()
        playlistDao = songPlaylistDb.playlistDao()
        songDao = songPlaylistDb.songDao()
    }

    @Test
    fun insertAndGetPlaylist(){
        runBlocking {
            val insertJob = launch {
                playlistDao.insertPlaylist(playlists[0])
            }
            val getJob = async {
                playlistDao.getPlaylist(1)
            }
            listOf(insertJob, getJob).joinAll()
            val receivedPlaylist = getJob.await()

            assertEquals("Rap", receivedPlaylist?.playlistName)
        }
    }

    @Test
    fun insertAndDeletedPlaylist(){
        runBlocking {
            var receivedPlaylist: Playlist? = null
            val insertJob = launch {
                playlistDao.insertPlaylist(playlists[0])
            }
            val getJob = async {
                playlistDao.getPlaylist(1)
            }
            val deleteJob = launch {
                receivedPlaylist = getJob.await()
                receivedPlaylist?.let {
                    playlistDao.deletePlaylist(it)
                }
            }
            listOf(insertJob, getJob, deleteJob).joinAll()
            assertEquals(null, playlistDao.getPlaylist(1)?.playlistName)
        }
    }

    @Test
    fun insertAndGetAllPlaylists(){
        runBlocking {
            val allPlaylists = playlistDao.getAllPlaylists()
            allPlaylists.blockingObserve()
            launch {
                for(playlist in playlists){
                    playlistDao.insertPlaylist(playlist)
                }
            }.join()
            assertEquals(3, allPlaylists.value?.size)
        }
    }

    @Test
    fun insertAndGetAllPlaylistWithSongs(){
        runBlocking {
            val playlistWithSongs = playlistDao.getPlaylistWithSongs()
            playlistWithSongs.blockingObserve()

            val insertPlJob = launch {
                playlistDao.insertPlaylist(playlists[0])
            }
            val insertSongsJob = launch {
                for (song in listOfSongs){
                    song.songId = 1
                    songDao.insertSong(song)
                }
            }
            listOf(insertPlJob, insertSongsJob).joinAll()
            Log.i("PlaylistWithSongsTest", "Playlist : ${playlistWithSongs.value}")
            assertEquals("Kanye West", playlistWithSongs.value?.let {
                it[0].listOfSongs[0].songArtist
            })
        }
    }


    @After
    fun tearDown(){
        songPlaylistDb.close()
    }
}