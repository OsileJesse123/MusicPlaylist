package com.jesse.musicplaylist.di

import android.content.Context
import androidx.room.Room
import com.jesse.musicplaylist.data.PlaylistDao
import com.jesse.musicplaylist.data.SongDao
import com.jesse.musicplaylist.data.SongPlaylistDatabase
import com.jesse.musicplaylist.migrations.MIGRATIONS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSongDao(database: SongPlaylistDatabase): SongDao{
        return database.songDao()
    }

    @Provides
    @Singleton
    fun providePlaylistDao(database: SongPlaylistDatabase): PlaylistDao{
        return database.playlistDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SongPlaylistDatabase{
        return Room.databaseBuilder(context, SongPlaylistDatabase::class.java,
            "song_playlist_database")
            .addMigrations(MIGRATIONS.migration_1_2)
            .createFromAsset("database/song.db")
            .build()
    }
}