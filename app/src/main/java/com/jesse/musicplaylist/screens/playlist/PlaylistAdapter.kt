package com.jesse.musicplaylist.screens.playlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesse.musicplaylist.R
import com.jesse.musicplaylist.data.Playlist
import com.jesse.musicplaylist.databinding.PlaylistCardLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

class PlaylistAdapter(val context: Context, val listener: OnPlaylistCardClickedListener):
    RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    interface OnPlaylistCardClickedListener{
        fun onPlaylistCardClicked(playlistUid: Int, playlistName: String)
    }

    var playlists: List<Playlist> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class PlaylistViewHolder(binding: PlaylistCardLayoutBinding):
        RecyclerView.ViewHolder(binding.root){

        var currentPlaylist: Playlist? = null

        val playlistName = binding.playlistNameTv
        private val plalistDate = binding.addedOnTv

        fun setData(position: Int) {
            currentPlaylist = playlists[position]
            playlistName.text = playlists[position].playlistName
            val addedOnDate = context.getString(R.string.added_on) + " " +
                    getFormattedDate(playlists[0].addedOn)
            plalistDate.text = addedOnDate
        }
        init {
            binding.root.setOnClickListener {
                listener.onPlaylistCardClicked(currentPlaylist?.uid
                    ?: 0, currentPlaylist?.playlistName ?: "Empty")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PlaylistCardLayoutBinding.inflate(layoutInflater, parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.setData(position)
    }

    override fun getItemCount(): Int = playlists.size


    private fun getFormattedDate(addedDate: Date): String {
        val sdf = SimpleDateFormat("HH:mm d MMM, yyyy", Locale.getDefault())
        return sdf.format(addedDate)
    }
}