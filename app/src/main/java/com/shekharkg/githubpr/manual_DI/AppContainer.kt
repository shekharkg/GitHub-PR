package com.shekharkg.githubpr.manual_DI

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.shekharkg.githubpr.MainViewModel
import com.shekharkg.githubpr.api.GitHubApiService
import com.shekharkg.githubpr.factories.MainViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.github.com")
        .build()
        .create(GitHubApiService::class.java)


    fun getMainViewModel(owner: ViewModelStoreOwner) =
        ViewModelProvider(owner, MainViewModelFactory(retrofit)).get(MainViewModel::class.java)

}