package com.isaquliyev.githubsearchapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isaquliyev.githubsearchapp.model.ItemRoom
import com.isaquliyev.githubsearchapp.room.ItemDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteListViewModel() : ViewModel() {
    private var favoriteList = MutableLiveData<List<ItemRoom>>()

    fun getFavoriteList( itemDao: ItemDAO){
        CoroutineScope(Dispatchers.IO).launch {
            favoriteList.postValue(itemDao.getAll())
        }
    }
    fun addToFavorites(itemDao: ItemDAO, itemRoom: ItemRoom){
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.insert(itemRoom)
        }
    }

    fun deleteFromFavorites(itemDao: ItemDAO, itemRoom: ItemRoom){
        CoroutineScope(Dispatchers.IO).launch {
            itemDao.delete(itemRoom)
            getFavoriteList(itemDao)
        }
    }

    fun observeFavoriteList(): LiveData<List<ItemRoom>> {
        return favoriteList
    }

}