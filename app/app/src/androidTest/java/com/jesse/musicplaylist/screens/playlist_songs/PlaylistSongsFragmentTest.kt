package com.jesse.musicplaylist.screens.playlist_songs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesse.musicplaylist.ClickOnImageView
import com.jesse.musicplaylist.R
import com.jesse.musicplaylist.screens.songs.SongsAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/*
NOTE: To run these tests, two new playlist objects will have to be
created, one with no songs added and another with songs added. After that,
click on both playlist objects, make sure to inspect the information log(Log.i() or info),
look out for the tag "PlaylistObjectDetails" there you should get the uid and name for each
playlist object. These should be put down somewhere and used as arguments to launch the
fragments in the respective scenario.
 */

@RunWith(AndroidJUnit4::class)
class PlaylistSongsFragmentTest {


    @Test
    fun test_emptyPlaylistSongAndNavigateToAddSongActivity(){
        //Make sure a playlist object without songs added to it is created then follow the
        //instructions in the NOTE section above before running the tests.
        //The playlistUid and playlistName obtained from the log info should be used to replace
        //playlistUid and playlistName properties below
        val playlistUid = 35
        val playlistName = "Goons"
        val fragmentArgs = bundleOf("playlistUid" to playlistUid,
            "playlistName" to playlistName)
        val scenario = launchFragmentInContainer<PlaylistSongsFragment>(fragmentArgs)
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        scenario.onFragment{
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.add_song_btn)).perform(click())

        assert(navController.currentDestination?.id == R.id.addSongActivity)
    }

    @Test
    fun test_nonEmptyPlaylistSong(){
        //Make sure a playlist object with songs added to it is created then follow the
        //instructions in the NOTE section above before running the tests.
        //The playlistUid and playlistName obtained from the log info should be used to replace
        //playlistUid and playlistName properties below
        val playlistUid = 6
        val playlistName = "Adonis"
        val fragmentArgs = bundleOf("playlistName" to playlistName,
            "playlistUid" to playlistUid)
        val scenario = launchFragmentInContainer<PlaylistSongsFragment>(fragmentArgs)
        onView(withId(R.id.playlist_songs_recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<SongsAdapter.SongsViewHolder>(1,
                ClickOnImageView(R.id.delete_song_iv)))
    }
}