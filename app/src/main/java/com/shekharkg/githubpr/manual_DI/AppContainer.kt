package com.shekharkg.githubpr.manual_DI

import com.shekharkg.githubpr.api.GitHubApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.github.com")
        .build()
        .create(GitHubApiService::class.java)

}