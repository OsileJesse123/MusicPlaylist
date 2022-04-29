package com.jesse.musicplaylist.database_test

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jesse.musicplaylist.blockingObserve
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.data.SongDao
import com.jesse.musicplaylist.data.SongPlaylistDatabase
import com.jesse.musicplaylist.repository.SongRepository
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

@RunWith(AndroidJUnit4::class)
class SongDaoTest {

    @get: Rule
    val instantTask = InstantTaskExecutorRule()

    private lateinit var songPlaylistDb: SongPlaylistDatabase
    private lateinit var songDao: SongDao
    private val listOfSongs = listOf(
        Song(songName = "Black Skin Head", songArtist = "Kanye West"),
        Song(songName = "That Way", songArtist = "Lil Uzi Vert"),
        Song(songName = "Felony", songArtist = "CKay")
    )
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp(){

        songPlaylistDb = Room.inMemoryDatabaseBuilder(context, SongPlaylistDatabase::class.java)
            .fallbackToDestructiveMigration()
            .build()
        songDao = songPlaylistDb.songDao()
    }

    @Test
    fun insertAndGetSong(){
        runBlocking {
            val insertJob = launch {
                songDao.insertSong(listOfSongs[0])
            }
            val getJob = async {
                songDao.getSong(1)
            }
            listOf(insertJob, getJob).joinAll()
            val receivedSong = getJob.await()

            assertEquals("Kanye West", receivedSong?.songArtist)
        }
    }

    @Test
    fun insertAndDeleteSong(){
        runBlocking{
            var receivedSong: Song? = null
            val insertJob = launch {
                songDao.insertSong(listOfSongs[0])
            }
            val getJob = async {
                songDao.getSong(1)
            }
            val deleteJob = launch {
                receivedSong = getJob.await()
                receivedSong?.let {
                    songDao.deleteSong(it)
                }
            }
            listOf(insertJob, getJob, deleteJob).joinAll()

            Log.i("Receive Song Test", "Artist: ${receivedSong?.songArtist}")

            assertEquals(null, songDao.getSong(1)?.songArtist)
        }
    }

    @Test
    fun insertAndGetAllSongs(){
        runBlocking {
            val allSongs = songDao.getAllSongs()
            allSongs.blockingObserve()
            launch {
                for(song in listOfSongs){
                    songDao.insertSong(song)
                }
            }.join()
            assertEquals(3, allSongs.value?.size)
        }
    }

    @Test
    fun insertAndSearchSongs(){
        runBlocking{
            val soughtOutSong = songDao.searchSongs("That Way")
            soughtOutSong.blockingObserve()
            launch {
                for(song in listOfSongs){
                    songDao.insertSong(song)
                }
            }.join()
            assertEquals("Lil Uzi Vert", soughtOutSong.value?.get(0)?.songArtist)
        }
    }

    @After
    fun tearDown(){
        songPlaylistDb.close()
    }

}