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
import com.isaquliyev.githubsearchapp.model.Item
import com.isaquliyev.githubsearchapp.utils.OnItemClickListener
import com.squareup.picasso.Picasso

class RepositoryAdapter(var list : List<Item>, val listener : OnItemClickListener) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.usernameId)
        val repositoryName : TextView= itemView.findViewById(R.id.repositoryName)
        val profilePhoto : ImageView = itemView.findViewById(R.id.profilePhoto)
        val description : TextView = itemView.findViewById(R.id.descriptionId)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RepositoryAdapter.ViewHolder, position: Int) {
        holder.username.text = "Username: ${list[position].owner.login}"
        holder.repositoryName.text = "Repository: ${list[position].name}"
        holder.description.text = "Description: ${list[position].description}"
        if(list[position].description != null ) {
            holder.description.text = "Description: ${list[position].description}"
        }
        else{
            holder.description.setText(R.string.defaultText)
        }
        Picasso.get().load(list[position].owner.avatar_url).into(holder.profilePhoto)
        holder.itemView.setOnClickListener {
            listener.onCLick(list[position])
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}