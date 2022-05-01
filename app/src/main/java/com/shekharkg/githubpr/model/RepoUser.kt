package com.shekharkg.githubpr.model

import com.google.gson.annotations.SerializedName

data class RepoUser(
    @SerializedName("login")
    val name: String?,
    @SerializedName("avatar_url")
    val avatar: String?
)