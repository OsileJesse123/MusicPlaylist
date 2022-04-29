package com.jesse.musicplaylist.screens.playlist


import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesse.musicplaylist.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlaylistFragmentTest {


    @Test
    fun test_createNewPlaylistCancel(){
        val scenario = launchFragmentInContainer<PlaylistFragment>()
        onView(withId(R.id.create_playlist_fab)).perform(click())
        onView(withId(R.id.cancel_btn)).perform(click())
    }

    @Test
    fun test_createNewPlaylistSaveWithoutName(){
        val scenario = launchFragmentInContainer<PlaylistFragment>()
        onView(withId(R.id.create_playlist_fab)).perform(click())
        onView(withId(R.id.save_btn)).perform(click())
    }

    @Test
    fun test_createNewPlaylistSaveWithName(){
        val playlistName = "Rider"
        val scenario = launchFragmentInContainer<PlaylistFragment>()
        onView(withId(R.id.create_playlist_fab)).perform(click())
        onView(withId(R.id.playlist_name_et)).perform(typeText(playlistName))
        closeSoftKeyboard()
        onView(withId(R.id.save_btn)).perform(click())
    }

    @Test
    fun test_createMultiplePlaylistsAndSwipeToDelete(){
        val playlistName = "Goon"
        val playlistName2 = "Bahd"
        val scenario = launchFragmentInContainer<PlaylistFragment>()

        //Create first playlist
        onView(withId(R.id.create_playlist_fab)).perform(click())
        onView(withId(R.id.playlist_name_et)).perform(typeText(playlistName))
        onView(withId(R.id.save_btn)).perform(click())

        //Create second playlist
        onView(withId(R.id.create_playlist_fab)).perform(click())
        onView(withId(R.id.playlist_name_et)).perform(typeText(playlistName2))
        onView(withId(R.id.save_btn)).perform(click())

        //Close soft keyboard
        closeSoftKeyboard()

        //Delete both playlists
        onView(withId(R.id.playlist_recycler)).perform(RecyclerViewActions
            .actionOnItem<PlaylistAdapter.PlaylistViewHolder>(hasDescendant(withText(playlistName)), swipeLeft()))
        onView(withId(R.id.playlist_recycler)).perform(RecyclerViewActions
            .actionOnItem<PlaylistAdapter.PlaylistViewHolder>(hasDescendant(withText(playlistName2)), swipeRight()))
    }

    @Test
    fun test_clickOnPlaylistItemAndNavigateToPlaylistSongsFragment(){
        val playlistName = "Goons"
        val scenario = launchFragmentInContainer<PlaylistFragment>()
        val navController = TestNavHostController(ApplicationProvider
            .getApplicationContext())


        //Create first playlist
        onView(withId(R.id.create_playlist_fab)).perform(click())
        onView(withId(R.id.playlist_name_et)).perform(typeText(playlistName))
        onView(withId(R.id.save_btn)).perform(click())

        scenario.onFragment{ fragment ->
           navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        //Click on playlist item
        onView(withId(R.id.playlist_recycler)).perform(RecyclerViewActions
            .actionOnItem<PlaylistAdapter.PlaylistViewHolder>(
                hasDescendant(withText(playlistName)), click()))
        assert(navController.currentDestination?.id == R.id.playlistSongsFragment)

    }
}