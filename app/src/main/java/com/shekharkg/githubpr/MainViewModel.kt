package com.shekharkg.githubpr

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shekharkg.githubpr.api.GitHubApiService
import com.shekharkg.githubpr.model.PullRequest
import com.shekharkg.githubpr.model.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val GitHubUser = "shekharkg"
const val TAG = "GitHub_PR"

class MainViewModel(private val apiService: GitHubApiService) : ViewModel() {


    private val _repositories: ArrayList<Repository> = ArrayList()
    private val _pullRequest: ArrayList<PullRequest> = ArrayList()
    private val _pullRequestLiveData: MutableLiveData<List<PullRequest>> = MutableLiveData()
    private var pageNumber = 1

    init {
        fetchRepositories(GitHubUser)
    }

    fun getPullRequest(): LiveData<List<PullRequest>> = _pullRequestLiveData

    private fun fetchRepositories(user: String) {
        Log.e(TAG, "Fetching REPO for: $user")
        apiService.getRepository(user = user, page = pageNumber)
            .enqueue(object : Callback<List<Repository>> {
                override fun onResponse(
                    call: Call<List<Repository>>,
                    response: Response<List<Repository>>
                ) {
                    Log.e(TAG, "Repos status : ${response.isSuccessful}")
                    if (response.isSuccessful) {
                        Log.e(TAG, "Repos count : ${response.body()?.size}")
                        response.body()?.let { repositories ->
                            if (repositories.size == 30) {
                                pageNumber++
                                fetchRepositories(user)
                            }

                            for (repository in repositories) {
                                _repositories.add(repository)
                                if (repository.name != null) {
                                    fetchClosedPullRequests(user, repository.name)
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }

            })
    }


    private fun fetchClosedPullRequests(user: String, repoName: String) {
        Log.e(TAG, "Fetching PR : $user <> $repoName")
        apiService.getClosedPrs(user, repoName).enqueue(object : Callback<List<PullRequest>> {
            override fun onResponse(
                call: Call<List<PullRequest>>,
                response: Response<List<PullRequest>>
            ) {
                Log.e(TAG, "PR status : ${response.isSuccessful}")
                if (response.isSuccessful) {
                    Log.e(TAG, "PR count for Repo: $repoName is ${response.body()?.size}")
                    response.body()?.let {
                        for (pr in it) {
                            _pullRequest.add(pr)
                            _pullRequestLiveData.value = _pullRequest
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<PullRequest>>, t: Throwable) {
                Log.e(TAG, t.toString())
            }

        })
    }


}