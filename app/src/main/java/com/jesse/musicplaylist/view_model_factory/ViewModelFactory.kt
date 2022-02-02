package com.jesse.musicplaylist.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jesse.musicplaylist.repository.PlaylistRepository
import com.jesse.musicplaylist.repository.SongRepository
import com.jesse.musicplaylist.screens.add_song.AddSongViewModel
import com.jesse.musicplaylist.screens.playlist.PlaylistViewModel
import com.jesse.musicplaylist.screens.playlist_songs.PlaylistSongsViewModel
import com.jesse.musicplaylist.screens.search.SearchViewModel
import com.jesse.musicplaylist.screens.songs.SongsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val playlistRepo: PlaylistRepository,
                       private val songRepo: SongRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass){
            when{
                isAssignableFrom(AddSongViewModel::class.java) ->
                    AddSongViewModel(songRepo)
                isAssignableFrom(PlaylistViewModel::class.java) ->
                    PlaylistViewModel(playlistRepo, songRepo)
                isAssignableFrom(PlaylistSongsViewModel::class.java) ->
                    PlaylistSongsViewModel(songRepo)
                isAssignableFrom(SearchViewModel::class.java) ->
                    SearchViewModel(songRepo)
                isAssignableFrom(SongsViewModel::class.java) ->
                    SongsViewModel(songRepo)
                else -> IllegalAccessException("Unknown ViewModel Class")
            }
        } as T
    }
}