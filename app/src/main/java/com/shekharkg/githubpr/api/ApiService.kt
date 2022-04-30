package com.shekharkg.githubpr.api

import com.shekharkg.githubpr.model.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {

    @GET("/users/{user}/repos?page={pageNo}")
    fun getRepository(
        @Path("user") user: String,
        @Query("pageNo") page: Int
    ): Call<Repository>


    @GET("/repos/{user}/{repo}/pulls?state=closed")
    fun getClosedPrs(
        @Path("user") user: String,
        @Query("repo") repo: String
    ): Call<Repository>



}