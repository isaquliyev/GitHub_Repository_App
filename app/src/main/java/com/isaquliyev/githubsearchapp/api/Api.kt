package com.isaquliyev.githubsearchapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("search/repositories")
    fun getRepositories(@Query("q") createdDate : String , @Query("sort") sort : String, @Query("order") order : String)
}