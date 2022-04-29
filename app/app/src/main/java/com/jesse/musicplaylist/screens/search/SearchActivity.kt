package com.jesse.musicplaylist.screens.search

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesse.musicplaylist.databinding.SearchActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: SearchActivityBinding
    private val searchAdapter = SearchAdapter()
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun setupRecyclerView() {
        binding.searchRecycler.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity,
                LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }

    private fun handleIntent(intent: Intent){
        if(Intent.ACTION_SEARCH == intent.action){
            val searchQuery = intent.getStringExtra(SearchManager.QUERY)
            searchViewModel.searchSongs("%$searchQuery%").observe(this){
                songs -> songs?.let {
                    searchAdapter.setSearchResults(it)
                }
            }
        }
    }
}