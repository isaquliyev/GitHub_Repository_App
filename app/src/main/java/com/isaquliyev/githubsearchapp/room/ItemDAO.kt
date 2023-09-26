package com.isaquliyev.githubsearchapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.isaquliyev.githubsearchapp.model.ItemRoom

@Dao
interface ItemDAO {
    @Query("SELECT * FROM Item_Favorites")
    fun getAll(): List<ItemRoom>

    @Insert
    fun insert(vararg item: ItemRoom)

    @Delete
    fun delete(item: ItemRoom)
}