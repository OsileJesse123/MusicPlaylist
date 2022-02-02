package com.jesse.musicplaylist.screens.songs

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesse.musicplaylist.ClickOnImageView
import com.jesse.musicplaylist.R
import org.junit.Test
import org.junit.runner.RunWith

/*
Please note that a custom view action class was created to allow espresso
interact with views within recycler view items. This class is called
ClickOnImageView and is located in the androidTest directory.
 */

@RunWith(AndroidJUnit4::class)
class SongsFragmentTest{

    @Test
    fun test_isSongFragmentVisibleAndCanDeleteSongItem(){
        val scenario = launchFragmentInContainer<SongsFragment>()
        //For this test to run successfully the recycler view should have at least 2
        //song objects in place.
        onView(withId(R.id.song_recycler)).perform(RecyclerViewActions
            .actionOnItemAtPosition<SongsAdapter.SongsViewHolder>(1,
                ClickOnImageView(R.id.delete_song_iv)))
    }
}