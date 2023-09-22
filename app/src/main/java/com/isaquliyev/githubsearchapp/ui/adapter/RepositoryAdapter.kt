package com.isaquliyev.githubsearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.isaquliyev.githubsearchapp.R
import com.isaquliyev.githubsearchapp.model.Item
import com.squareup.picasso.Picasso

class RepositoryAdapter(var list : List<Item>) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val username = itemView.findViewById<TextView>(R.id.usernameId)
        val repositoryName = itemView.findViewById<TextView>(R.id.repositoryName)
        val profilePhoto = itemView.findViewById<ImageView>(R.id.profilePhoto)
        val description = itemView.findViewById<TextView>(R.id.descriptionId)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent, false)
        )
    }

    override fun onBindViewHolder(holder: RepositoryAdapter.ViewHolder, position: Int) {
        holder.username.setText(list.get(position).owner.login)
        holder.repositoryName.setText(list.get(position).name)
        holder.description.setText(list.get(position).description)
        Picasso.get().load(list.get(position).owner.avatar_url).into(holder.profilePhoto)

    }

    override fun getItemCount(): Int {
        return list.size
    }


}