package com.jesse.musicplaylist.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object MIGRATIONS {
    val migration_1_2 = object : Migration(1,2){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE song ADD COLUMN song_states TEXT DEFAULT NULL")
        }

    }
}