package com.jesse.musicplaylist.screens


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesse.musicplaylist.ClickOnImageView
import com.jesse.musicplaylist.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_navigateToPlaylistFragmentThenBackToSongsFragment(){
        //Launch MainActivity
        activityScenarioRule.scenario

        //Navigate between both fragments
        onView(withId(R.id.bottom_nav)).perform(ClickOnImageView(R.id.playlistFragment))
        onView(withId(R.id.bottom_nav)).perform(ClickOnImageView(R.id.songsFragment))
    }

    @Test
    fun test_searchForSongOrArtist(){
        //Launch MainActivity
        activityScenarioRule.scenario
        val artistName = "CKay"

        //Search for Artist
        onView(withId(R.id.search_menu_item)).perform(click())
        onView(withId(R.id.search_menu_item)).perform(typeText(artistName))
        pressImeActionButton()
    }
}