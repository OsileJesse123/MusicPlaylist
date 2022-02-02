package com.jesse.musicplaylist

import android.app.Application
import com.jesse.musicplaylist.data.SongPlaylistDatabase
import com.jesse.musicplaylist.repository.PlaylistRepository
import com.jesse.musicplaylist.repository.SongRepository

class MusicPlaylistApplication: Application() {
    val songRepository: SongRepository get() = SongRepository(
        SongPlaylistDatabase
            .getInstance(this.applicationContext).songDao())
    val playlistRepository: PlaylistRepository get() =
        PlaylistRepository(SongPlaylistDatabase
            .getInstance(this.applicationContext).playlistDao())
}