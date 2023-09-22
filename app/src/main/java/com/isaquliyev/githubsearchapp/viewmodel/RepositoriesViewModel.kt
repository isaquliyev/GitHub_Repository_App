package com.isaquliyev.githubsearchapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isaquliyev.githubsearchapp.model.ApiResponse
import com.isaquliyev.githubsearchapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoriesViewModel : ViewModel() {
    private var repositoryLiveData = MutableLiveData<ApiResponse>()
    fun getRepositories(createdDate: String, sort: String, order: String) {
        RetrofitClient.api.getRepositories(createdDate = createdDate, sort = sort, order = order)
            .enqueue(object : ViewModelInterface {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.body() != null) {
                        repositoryLiveData.value = response.body()
                    } else {
                        return
                    }
                }
            })
    }
    fun observeRepositoryLiveData() : LiveData<ApiResponse>{
        return repositoryLiveData
    }
}