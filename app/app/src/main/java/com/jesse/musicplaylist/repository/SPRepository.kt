package com.jesse.musicplaylist.repository

import androidx.lifecycle.LiveData

interface SPRepository <T> {

    suspend fun insertObject(obj: T)
    suspend fun deleteObject(obj: T)
    suspend fun getObject(index: Int): T?
    suspend fun updateObject(obj: T)
    fun getAllObjects(): LiveData<List<T>>
    fun searchObjects(searchQuery: String): LiveData<List<T>>
}