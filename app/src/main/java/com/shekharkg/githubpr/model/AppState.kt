package com.shekharkg.githubpr.model

sealed class NetworkResult(
    val message: String? = null
) {

    class Success() : NetworkResult()

    class Error(message: String?) : NetworkResult(message)

    class Loading : NetworkResult()

}