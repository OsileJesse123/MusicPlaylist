package com.jesse.musicplaylist.screens.songs

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.databinding.SongCardLayoutBinding

open class SongsAdapter(): RecyclerView.Adapter<SongsAdapter.SongsViewHolder>() {

    private var deleteSongLambda: ((song: Song) -> Unit)? = null
    private var songs: List<Song> = mutableListOf()

        inner class SongsViewHolder(binding: SongCardLayoutBinding):
            RecyclerView.ViewHolder(binding.root){

            var currentSong: Song? = null
            val songTitleTv = binding.songTitle
            val songArtistTv = binding.songArtist
            val deleteSongIv = binding.deleteSongIv

            fun setData(song: Song) {
                songTitleTv.text = song.songName
                songArtistTv.text = song.songArtist
                Log.v("CheckSong", "${song.uid} is the song number")
                currentSong = song
            }

            init {
                deleteSongIv.setOnClickListener {
                    currentSong?.let {
                        //deleteClickListener?.onDeleteClickListener(it)
                        deleteSongLambda?.invoke(it)
                    }
                }
            }

        }

    fun setSongs(songs: List<Song>){
        this.songs = songs
        notifyDataSetChanged()
    }

    fun setDeleteLambda(deleteLambda: (song: Song) -> Unit){
        deleteSongLambda = deleteLambda
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SongCardLayoutBinding.inflate(layoutInflater, parent, false)
        return SongsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.setData(songs[position])
    }

    override fun getItemCount(): Int = songs.size
}