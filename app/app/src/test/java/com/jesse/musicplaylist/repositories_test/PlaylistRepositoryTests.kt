package com.jesse.musicplaylist.repositories_test

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesse.musicplaylist.data.Playlist
import com.jesse.musicplaylist.data.SongPlaylistDatabase
import com.jesse.musicplaylist.repository.PlaylistRepository
import com.jraska.livedata.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class PlaylistRepositoryTests {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: SongPlaylistDatabase
    private val date = Calendar.getInstance().time

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, SongPlaylistDatabase::class.java)
            .build()
    }

    @Test
    fun test_getObject(){
        runBlocking(Dispatchers.IO) {
            //Arrange
            db.playlistDao().insertPlaylist(Playlist(playlistName = "Rock", addedOn = date))
            val expected = "Rock"
            val repo = PlaylistRepository(db.playlistDao())

            //Act
            val actual = repo.getObject(1)?.playlistName

            //Assert
            assertEquals(expected, actual)
        }
    }

    @Test
    fun test_insertObject(){
        runBlocking(Dispatchers.IO){
            //Arrange
            val playlist = Playlist(playlistName = "Rock", addedOn = date)
            val repo = PlaylistRepository(db.playlistDao())

            //Act
            repo.insertObject(playlist)

            //Assert
            assertNotNull(repo.getObject(1))
        }
    }

    @Test
    fun test_deleteObject(){
        runBlocking(Dispatchers.IO){
            //Arrange
            db.playlistDao().insertPlaylist(Playlist(playlistName = "Rock", addedOn = date))
            val repo = PlaylistRepository(db.playlistDao())
            val retrievedPlaylist = repo.getObject(1)

            //Assert
            assertNotNull(retrievedPlaylist)

            //Act
            repo.deleteObject(retrievedPlaylist!!)

            //Assert
            assertNull(repo.getObject(1))
        }
    }

    @Test
    fun test_getAllObjects(){
        runBlocking(Dispatchers.IO) {
            //Arrange
            db.playlistDao().insertPlaylist(Playlist(playlistName = "Rock", addedOn = date))
            db.playlistDao().insertPlaylist(Playlist(playlistName = "Roll", addedOn = date))
            db.playlistDao().insertPlaylist(Playlist(playlistName = "Alone", addedOn = date))
            val repo = PlaylistRepository(db.playlistDao())
            val expected = 3

            //Act
            val actual = repo.getAllObjects().test().value().size

            //Assert
            assertEquals(expected, actual)
        }
    }
    @After
    fun tearDown(){
        db.close()
    }
}