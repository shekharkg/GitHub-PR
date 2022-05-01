package com.shekharkg.githubpr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shekharkg.githubpr.api.GitHubApiService
import com.shekharkg.githubpr.model.NetworkResult
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
    private var _pageNumber = 1
    private var _apiCallsInProgress = 0
    private var _networkState: MutableLiveData<NetworkResult> = MutableLiveData()


    init {
//        fetchRepositories(GitHubUser)
    }

    fun getPullRequest(): LiveData<List<PullRequest>> = _pullRequestLiveData
    fun getNetworkState(): LiveData<NetworkResult> = _networkState

    private fun fetchRepositories(user: String) {
        _apiCallsInProgress++
        updateNetworkState(null)
        apiService.getRepository(user = user, page = _pageNumber)
            .enqueue(object : Callback<List<Repository>> {
                override fun onResponse(
                    call: Call<List<Repository>>,
                    response: Response<List<Repository>>
                ) {
                    _apiCallsInProgress--

                    if (response.isSuccessful) {
                        updateNetworkState(null)
                        response.body()?.let { repositories ->
                            if (repositories.size == 30) {
                                _pageNumber++
                                fetchRepositories(user)
                            }

                            for (repository in repositories) {
                                _repositories.add(repository)
                                if (repository.name != null) {
                                    fetchClosedPullRequests(user, repository.name)
                                }
                            }
                        }
                    } else {
                        response.errorBody()?.let {
                            updateNetworkState(it.toString())
                        } ?: updateNetworkState("Something went wrong")

                    }
                }

                override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                    _apiCallsInProgress--
                    updateNetworkState(t.toString())
                }

            })
    }


    private fun fetchClosedPullRequests(user: String, repoName: String) {
        _apiCallsInProgress++
        updateNetworkState(null)
        apiService.getClosedPrs(user, repoName).enqueue(object : Callback<List<PullRequest>> {
            override fun onResponse(
                call: Call<List<PullRequest>>,
                response: Response<List<PullRequest>>
            ) {
                _apiCallsInProgress--

                if (response.isSuccessful) {
                    updateNetworkState(null)
                    response.body()?.let {
                        for (pr in it) {
                            _pullRequest.add(pr)
                        }

                        if(it.isNotEmpty()){
                            _pullRequestLiveData.value = _pullRequest
                        }
                    }
                } else {
                    response.errorBody()?.let {
                        updateNetworkState(it.toString())
                    } ?: updateNetworkState("Something went wrong")

                }
            }

            override fun onFailure(call: Call<List<PullRequest>>, t: Throwable) {
                _apiCallsInProgress--
                updateNetworkState(t.toString())
            }

        })
    }

    private fun updateNetworkState(message: String?) {
        if (_apiCallsInProgress == 0) {
            if (message == null) {
                _networkState.value = NetworkResult.Success()
            } else {
                _networkState.value = NetworkResult.Error(message)
            }
        } else if (_apiCallsInProgress > 0) {
            _networkState.value = NetworkResult.Loading()
        }
    }


}