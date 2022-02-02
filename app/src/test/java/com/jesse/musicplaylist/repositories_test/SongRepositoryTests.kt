package com.jesse.musicplaylist.repositories_test

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.data.SongPlaylistDatabase
import com.jesse.musicplaylist.repository.SongRepository
import com.jraska.livedata.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SongRepositoryTests {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: SongPlaylistDatabase


    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, SongPlaylistDatabase::class.java)
            .build()

    }

    @Test
    fun test_getObject(){
        runBlocking(Dispatchers.IO){
            //Arrange
            db.songDao().insertSong(Song(songArtist = "CKay", songName = "Felony"))
            db.songDao().insertSong(Song(songArtist = "CKay", songName = "Felony"))
            db.songDao().insertSong(Song(songArtist = "CKay", songName = "Felony"))
            val repo = SongRepository(db.songDao())
            val expected = 3

            //Act
            val actual = repo.getAllObjects().test().value().size

            //Assert
            assertEquals(expected, actual)
        }
    }

    @Test
    fun test_getObjectSongRepo(){
        runBlocking(Dispatchers.IO){
            //Arrange
            db.songDao().insertSong(Song(songId = 1, songArtist = "CKay", songName = "Felony"))
            val repo = SongRepository(db.songDao())
            val expected = "CKay"

            //Act
            val actual = repo.getObject(1)?.songArtist

            //Assert
            assertEquals(expected, actual)
        }
    }

    @Test
    fun test_insertObject(){
        runBlocking(Dispatchers.IO){
            //Arrange
            val song = Song(songId = 1, songArtist = "CKay", songName = "Felony")
            val repo = SongRepository(db.songDao())
            val expected = "CKay"

            //Act
            repo.insertObject(song)
            val retrievedSong = repo.getObject(1)

            //Assert
            assertEquals(expected, retrievedSong?.songArtist)
        }
    }

    @Test
    fun test_deleteObject(){
        runBlocking(Dispatchers.IO){
            //Arrange
            val song = Song(songArtist = "CKay", songName = "Felony")
            val repo = SongRepository(db.songDao())
            repo.insertObject(song)

            //Assert
            assertNotNull(repo.getObject(1))

            //Act
            repo.deleteObject(repo.getObject(1)!!)

            //Assert
            assertNull(repo.getObject(1))
        }
    }

    @Test
    fun test_updateObject(){
        runBlocking(Dispatchers.IO){
            //Arrange
            db.songDao().insertSong(Song(songArtist = "CKay", songName = "Felony"))
            val repo = SongRepository(db.songDao())
            var retrievedSong = repo.getObject(1)

            //Assert
            assertEquals(0, retrievedSong?.songId)

            //Act
            retrievedSong?.songId = 1
            repo.updateObject(retrievedSong!!)

            //Assert
            assertEquals(1, retrievedSong?.songId)


        }
    }

    @Test
    fun test_searchObjectsByArtist(){
        runBlocking(Dispatchers.IO){
            //Arrange
            db.songDao().insertSong(Song(songArtist = "CKay", songName = "Felony"))
            db.songDao().insertSong(Song(songArtist = "CKay", songName = "Felony"))
            db.songDao().insertSong(Song(songArtist = "CKay", songName = "Felony"))
            val repo = SongRepository(db.songDao())
            val searchQuery = "CKay"
            val expected = 3

            //Act
            val actual = repo.searchObjects(searchQuery).test().value().size

            //Assert
            assertEquals(expected, actual)
        }
    }

    @Test
    fun test_searchObjectsByName(){
        runBlocking(Dispatchers.IO){
            //Arrange
            db.songDao().insertSong(Song(songArtist = "CKay", songName = "Felony"))
            db.songDao().insertSong(Song(songArtist = "CKay", songName = "Felony"))
            db.songDao().insertSong(Song(songArtist = "CKay", songName = "Felony"))
            val repo = SongRepository(db.songDao())
            val searchQuery = "Felony"
            val expected = 3

            //Act
            val actual = repo.searchObjects(searchQuery).test().value().size

            //Assert
            assertEquals(expected, actual)
        }
    }

    @After
    fun tearDown(){
        db.close()
    }
}