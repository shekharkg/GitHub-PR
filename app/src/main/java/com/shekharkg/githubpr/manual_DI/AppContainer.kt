package com.shekharkg.githubpr.manual_DI

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.shekharkg.githubpr.MainViewModel
import com.shekharkg.githubpr.api.Authenticator
import com.shekharkg.githubpr.api.GitHubApiService
import com.shekharkg.githubpr.factories.MainViewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {

    private val client = OkHttpClient.Builder()
        .addInterceptor(
            Authenticator(
                username = "shekharkg",
                password = "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"
            )
        )
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.github.com")
        .client(client)
        .build()
        .create(GitHubApiService::class.java)


    fun getMainViewModel(owner: ViewModelStoreOwner) =
        ViewModelProvider(owner, MainViewModelFactory(retrofit)).get(MainViewModel::class.java)

}

