package com.isaquliyev.githubsearchapp.model

import com.isaquliyev.githubsearchapp.R
import java.io.Serializable

data class ApiResponse(val total_count: String, val incomplete_result: String, val items: List<Item>)

data class Item(
    val id: Int,
    val name: String,
    val owner: Owner,
    val description: String? = R.string.description.toString(),
    val forks: Int,
    val stargazers_count : Int
) : Serializable

data class Owner(val login: String, val avatar_url: String)