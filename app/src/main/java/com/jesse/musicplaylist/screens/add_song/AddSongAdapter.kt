package com.jesse.musicplaylist.screens.add_song


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.databinding.AddSongCardLayoutBinding

class AddSongAdapter: RecyclerView.Adapter<AddSongAdapter.AddSongViewHolder>() {

    private var addSongHolder: AddSongViewHolder? = null

    //The addedSongs property was made a companion object to allow for assertion during testing
    companion object{
        val addedSongs = mutableListOf<Song>()
    }
    val cancelLambda = {
        if(addedSongs.isNotEmpty())
            addedSongs.removeAll(addedSongs)
    }
    var songs = listOf<Song>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    var addSongLambda:( (songs:List<Song>) -> Unit)? = null
    var noSongSelectedLambda: (() -> Unit)? = null

    inner class AddSongViewHolder(val binding: AddSongCardLayoutBinding):
        RecyclerView.ViewHolder(binding.root){

        var currentSong: Song? = null

        fun setData(position: Int) {
            currentSong = songs[position]
            binding.songArtist.text = songs[position].songArtist
            binding.songTitle.text = songs[position].songName
        }

        fun addSong(){
           if(addedSongs.isNotEmpty()){
               addSongLambda?.invoke(addedSongs)
           } else {
               noSongSelectedLambda?.invoke()
           }
        }
        init {
            binding.addSongCb.setOnClickListener {
                if (binding.addSongCb.isChecked){
                    addedSongs.add(currentSong!!)
                } else {
                    currentSong?.let {
                        if(it in addedSongs)
                            addedSongs.remove(it)
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddSongViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AddSongCardLayoutBinding.inflate(layoutInflater, parent, false)
        return AddSongViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AddSongViewHolder, position: Int) {
        addSongHolder = holder
        holder.setData(position)
    }

    override fun getItemCount(): Int = songs.size


    fun addTheSong(){
        addSongHolder?.addSong()
    }
}