package com.jesse.musicplaylist

import android.app.Application
import com.jesse.musicplaylist.data.SongPlaylistDatabase
import com.jesse.musicplaylist.repository.PlaylistRepository
import com.jesse.musicplaylist.repository.SongRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MusicPlaylistApplication: Application()