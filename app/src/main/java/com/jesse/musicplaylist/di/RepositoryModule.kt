package com.jesse.musicplaylist.di

import com.jesse.musicplaylist.data.Playlist
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.repository.PlaylistRepository
import com.jesse.musicplaylist.repository.SPRepository
import com.jesse.musicplaylist.repository.SongRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideSongRepo(songRepository: SongRepository): SPRepository<Song>

    @Binds
    @Singleton
    abstract fun providePlaylistRepo(playlistRepository: PlaylistRepository): SPRepository<Playlist>
}