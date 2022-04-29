package com.jesse.musicplaylist.screens

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.jesse.musicplaylist.R
import com.jesse.musicplaylist.data.SongPlaylistDatabase
import com.jesse.musicplaylist.databinding.ActivityMainBinding
import com.jesse.musicplaylist.screens.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var songPlaylistDatabase: SongPlaylistDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as
                NavHostFragment
        val navController = navHost.navController

        val toplevelFrags = setOf(R.id.songsFragment, R.id.playlistFragment)
        val appBarConfig = AppBarConfiguration(toplevelFrags)
        binding.bottomNav.setupWithNavController(navController)
        //Use this to setup label names for components using navcomp library
        NavigationUI.setupActionBarWithNavController(this, navController,appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.fragmentContainerView).navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val componentName = ComponentName(this, SearchActivity::class.java)
        (menu?.findItem(R.id.search_menu_item)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        songPlaylistDatabase.close()
    }
}