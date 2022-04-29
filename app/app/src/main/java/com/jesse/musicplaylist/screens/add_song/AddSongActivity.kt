package com.jesse.musicplaylist.screens.add_song

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesse.musicplaylist.R
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.databinding.AddSongActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddSongActivity : AppCompatActivity() {

    private lateinit var binding: AddSongActivityBinding
    @Inject
    lateinit var addSongAdapter: AddSongAdapter
    private val addSongViewModel: AddSongViewModel by viewModels()
    private val args: AddSongActivityArgs by navArgs()
    private lateinit var cancelLambda: (() -> Unit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddSongActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        initializeAllLambdas()
    }

    private fun updateSong(song: Song) {
        //intent was used to getIntExtra for testing purposes
        //The Activity is tested in isolation and requires a value for playlistUid
        val playlistUid = if(args.playlistUid == 0)
            intent.getIntExtra("playlistUid", 1) else args.playlistUid
        addSongViewModel.updateSongStates(playlistUid, song)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_song_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.add_songs -> {addSongAdapter.addTheSong()
                true
            }
            R.id.cancel_operation -> {
                cancelLambda.invoke()
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView(){
        binding.addSongActivityRecycler.apply {
            layoutManager = LinearLayoutManager(this@AddSongActivity,
                LinearLayoutManager.VERTICAL, false)
            adapter = addSongAdapter
        }

        addSongViewModel.getAllSongs().observe(this){
            songs -> addSongAdapter.songs = songs
        }
    }

    private fun initializeAllLambdas(){
        addSongAdapter.addSongLambda = {songs: List<Song> ->
            for(song in songs){
                updateSong(song)
            }
            finish()
        }
        addSongAdapter.noSongSelectedLambda = {
            Toast.makeText(this, "No song was selected", Toast.LENGTH_SHORT).show()
        }
        cancelLambda = addSongAdapter.cancelLambda
    }
}