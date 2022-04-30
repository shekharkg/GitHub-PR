package com.shekharkg.githubpr.model

data class PullRequest(
    val title: String?,
    val created_at: String?,
    val closed_at: String?,
    val user: RepoUser?
)

data class RepoUser(
    val login: String?,
    val avatar_url: String?
)