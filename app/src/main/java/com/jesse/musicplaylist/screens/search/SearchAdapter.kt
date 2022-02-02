package com.jesse.musicplaylist.screens.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.databinding.SearchCardLayoutBinding

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var searchResults = listOf<Song>()

    inner class SearchViewHolder(val binding: SearchCardLayoutBinding):
        RecyclerView.ViewHolder(binding.root){

        private val songName = binding.searchSongName
        private val songArtist = binding.searchSongArtist

        fun setData(position: Int) {
            val currentSong = searchResults[position]
            songName.text = currentSong.songName
            songArtist.text = currentSong.songArtist
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SearchCardLayoutBinding.inflate(layoutInflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.setData(position)
    }

    override fun getItemCount(): Int = searchResults.size

    fun setSearchResults(searchResults: List<Song>){
        this.searchResults = searchResults
        notifyDataSetChanged()
    }
}