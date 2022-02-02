package com.jesse.musicplaylist.screens.add_songs


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesse.musicplaylist.ClickOnImageView
import com.jesse.musicplaylist.R
import com.jesse.musicplaylist.screens.add_song.AddSongActivity
import com.jesse.musicplaylist.screens.add_song.AddSongAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddSongActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(AddSongActivity::class.java)
    private lateinit var activity: AddSongActivity

    @Test
    fun test_startAddSongActivityThenCancel(){
        val scenario = activityScenarioRule.scenario
        scenario.onActivity {
            activity = it
        }

        onView(withId(R.id.cancel_operation)).perform(click())

        //Assert that it finishes and goes back to PlaylistSongs
        assert(activity.isFinishing)
    }

    @Test
    fun test_startAddSongActivityThenClickAddSongs(){
        val scenario = activityScenarioRule.scenario
        onView(withId(R.id.add_songs)).perform(click())
    }

    @Test
    fun test_startActivitySelectSongsThenCancel(){
        //Launch Activity
        val scenario = activityScenarioRule.scenario
        scenario.onActivity {
            activity = it
        }

        //Select Songs
        val listOfStates = listOf(1,2,3)
        for (state in listOfStates){
            onView(withId(R.id.add_song_activity_recycler)).perform(RecyclerViewActions
                .actionOnItemAtPosition<AddSongAdapter
                .AddSongViewHolder>(state, ClickOnImageView(R.id.add_song_cb)))
        }
        //Cancel
        onView(withId(R.id.cancel_operation)).perform(click())

        //Assert that it finishes and goes back to PlaylistSongs
        //and also that no song was added to the addedSongs list in the AddsongAdapter
        assert(AddSongAdapter.addedSongs.isEmpty())
        assert(activity.isFinishing)
    }

    @Test
    fun test_startActivitySelectSongsThenAdd(){
        //Launch Activity
        val scenario = activityScenarioRule.scenario
        scenario.onActivity {
            activity = it
            it.intent.putExtra("playlistUid", 0)
        }
        //Select Songs
        val listOfStates = listOf(1,2,3)
        for (state in listOfStates){
            onView(withId(R.id.add_song_activity_recycler)).perform(RecyclerViewActions
                .actionOnItemAtPosition<AddSongAdapter
                .AddSongViewHolder>(state, ClickOnImageView(R.id.add_song_cb)))
        }

        //Add Songs
        onView(withId(R.id.add_songs)).perform(click())

        //Assert that it finishes and goes back to PlaylistSongs
        assert(AddSongAdapter.addedSongs.isNotEmpty())
        assert(activity.isFinishing)
    }

    @Test
    fun test_startActivitySelectUnselectReselectThenCancel(){
        //Launch Activity
        val scenario = activityScenarioRule.scenario
        scenario.onActivity {
            activity = it
        }

        //Select Songs
        val listOfScenarios = listOf(1,2,3,1,2,3,3,4,5)
        for(scenario in listOfScenarios){
            onView(withId(R.id.add_song_activity_recycler)).perform(RecyclerViewActions
                .actionOnItemAtPosition<AddSongAdapter
                .AddSongViewHolder>(scenario, ClickOnImageView(R.id.add_song_cb)))
        }
        //Cancel
        onView(withId(R.id.cancel_operation)).perform(click())

        //Assert that it finishes and goes back to PlaylistSongs
        assert(AddSongAdapter.addedSongs.isEmpty())
        assert(activity.isFinishing)
    }

    @Test
    fun test_startActivitySelectUnselectReselectThenAddSongs(){
        //Launch Activity
        val scenario = activityScenarioRule.scenario
        scenario.onActivity {
            activity = it
            it.intent.putExtra("playlistUid", 0)
        }

        //Select Songs
        val listOfScenarios = listOf(1,2,3,1,2,3,3,4,5)

        for(scenario in listOfScenarios){
            onView(withId(R.id.add_song_activity_recycler)).perform(RecyclerViewActions
                .actionOnItemAtPosition<AddSongAdapter
                .AddSongViewHolder>(scenario, ClickOnImageView(R.id.add_song_cb)))
        }
        onView(withId(R.id.add_songs)).perform(click())

        //Assert that it finishes and goes back to PlaylistSongs
        assert(AddSongAdapter.addedSongs.isNotEmpty())
        assert(activity.isFinishing)
    }
}