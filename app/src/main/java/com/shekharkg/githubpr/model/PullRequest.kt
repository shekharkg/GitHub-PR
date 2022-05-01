package com.shekharkg.githubpr.model

import com.google.gson.annotations.SerializedName

data class PullRequest(
    val title: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("closed_at")
    var closedAt: String?,
    val user: RepoUser?
)

