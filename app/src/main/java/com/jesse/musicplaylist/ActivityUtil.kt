package com.jesse.musicplaylist

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.jesse.musicplaylist.view_model_factory.ViewModelFactory

fun <T: ViewModel> Activity.obtainViewModel(viewModelClass: Class<T>): T {
    val application = this.application as MusicPlaylistApplication
    val songRepo = application.songRepository
    val playlistRepo = application.playlistRepository
    val viewModelFactory = ViewModelFactory(playlistRepo, songRepo)

    return ViewModelProvider(this as ViewModelStoreOwner, viewModelFactory).get(viewModelClass)
}