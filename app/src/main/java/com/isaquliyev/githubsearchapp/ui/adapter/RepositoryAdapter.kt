package com.isaquliyev.githubsearchapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.isaquliyev.githubsearchapp.R
import com.isaquliyev.githubsearchapp.databinding.RecyclerRowBinding
import com.isaquliyev.githubsearchapp.model.Item
import com.isaquliyev.githubsearchapp.model.ItemRoom
import com.isaquliyev.githubsearchapp.model.Owner
import com.isaquliyev.githubsearchapp.utils.OnFavoriteButtonClickListener
import com.isaquliyev.githubsearchapp.utils.OnItemClickListener
import com.squareup.picasso.Picasso

class RepositoryAdapter(
    private var list: List<Item>,
    private val listener: OnItemClickListener,
    private var favoriteListener: OnFavoriteButtonClickListener,
    var favoriteList: List<ItemRoom>,
) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {


    private var favoriteItemList = mutableListOf<Item>()

    inner class ViewHolder(var binding: RecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RepositoryAdapter.ViewHolder {
        return ViewHolder(
            RecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RepositoryAdapter.ViewHolder, position: Int) {
        holder.binding.usernameId.text = "Username: ${list[position].owner.login}"
        holder.binding.repositoryName.text = "Repository: ${list[position].name}"
        holder.binding.descriptionId.text = "Description: ${list[position].description}"
        if (list[position].description != null) {
            holder.binding.descriptionId.text = "Description: ${list[position].description}"
        } else {
            holder.binding.descriptionId.setText(R.string.defaultText)
        }
        Picasso.get().load(list[position].owner.avatar_url).into(holder.binding.profilePhoto)
        holder.itemView.setOnClickListener {
            listener.onCLick(list[position])
        }

        holder.binding.favoriteButton.isSelected = favoriteItemList.contains(list[position])


        holder.binding.favoriteButton.setOnClickListener {
            it.isSelected = (!it.isSelected)
            val itemRoom = ItemRoom(
                list[position].id,
                list[position].name,
                list[position].full_name,
                list[position].private,
                list[position].owner.login,
                list[position].owner.avatar_url,
                list[position].description,
                list[position].stargazers_count,
                list[position].language,
                list[position].forks,
                list[position].created_at,
                list[position].html_url
            )
            favoriteListener.onCLick(itemRoom, it.isSelected)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(itemList: List<Item>, favoriteItemList: List<ItemRoom>) {
        list = itemList
        favoriteList = favoriteItemList
        for (i in favoriteList.indices) {
            val item = Item(
                favoriteList[i].id,
                favoriteList[i].name,
                favoriteList[i].full_name,
                favoriteList[i].private,
                Owner(favoriteList[i].login, favoriteList[i].avatar_url),
                favoriteList[i].description,
                favoriteList[i].stargazers_count,
                favoriteList[i].language,
                favoriteList[i].forks,
                favoriteList[i].created_at,
                favoriteList[i].html_url
            )
            this.favoriteItemList.add(item)
        }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return list.size
    }



}