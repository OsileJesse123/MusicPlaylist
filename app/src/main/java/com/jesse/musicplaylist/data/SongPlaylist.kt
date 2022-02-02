package com.jesse.musicplaylist.data

import androidx.room.*
import java.util.*

@Entity(tableName = "song")
data class Song(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "song_name")
    val songName: String,
    @ColumnInfo(name = "song_artist")
    val songArtist: String,
    @ColumnInfo(name = "song_id")
    var songId: Int = 0,
    @ColumnInfo(name = "song_states")
    var songStates: MutableList<Int>? = null
)

@Entity(tableName = "playlist")
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "playlist_name")
    var playlistName: String,
    @ColumnInfo(name = "added_on")
    val addedOn: Date
)

data class PlaylistWithSongs(
    @Embedded
    val playlist: Playlist,
    @Relation(
        parentColumn = "uid",
        entityColumn = "song_id"
    )
    val listOfSongs: List<Song>
)
