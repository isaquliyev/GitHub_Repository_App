package com.isaquliyev.githubsearchapp.api

import com.isaquliyev.githubsearchapp.model.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("search/repositories")
    fun getRepositories(@Query("q") createdDate : String , @Query("sort") sort : String, @Query("order") order : String, @Query("page") page : String) : Call<ApiResponse>
}