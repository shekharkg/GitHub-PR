package com.shekharkg.githubpr.manual_DI

import com.shekharkg.githubpr.api.GitHubApiService
import retrofit2.Retrofit

class AppContainer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .build()
        .create(GitHubApiService::class.java)

}