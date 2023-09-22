package com.isaquliyev.githubsearchapp.viewmodel

import com.isaquliyev.githubsearchapp.model.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ViewModelInterface : Callback<ApiResponse> {
    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
        //
    }

    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
        //
    }
}