package com.isaquliyev.githubsearchapp.utils

import com.isaquliyev.githubsearchapp.model.ItemRoom

interface OnFavoriteButtonClickListener {

    fun onCLick(item: ItemRoom, isAddFavorite: Boolean)

}