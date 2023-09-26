package com.isaquliyev.githubsearchapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.isaquliyev.githubsearchapp.model.ItemRoom

@Database(entities = [ItemRoom::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDAO
}