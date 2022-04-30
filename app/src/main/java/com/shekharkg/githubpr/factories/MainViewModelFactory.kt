package com.shekharkg.githubpr.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shekharkg.githubpr.MainViewModel
import com.shekharkg.githubpr.api.GitHubApiService

class MainViewModelFactory(private val gitHubService: GitHubApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(this.gitHubService) as T
    }

}