package com.shekharkg.githubpr.model

import com.google.gson.annotations.SerializedName

data class PullRequest(
    val title: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("closed_at")
    val closedAt: String?,
    val user: RepoUser?
)

