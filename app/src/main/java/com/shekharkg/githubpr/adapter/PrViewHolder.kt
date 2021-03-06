package com.shekharkg.githubpr.adapter

import androidx.recyclerview.widget.RecyclerView
import com.shekharkg.githubpr.databinding.ItemPrBinding
import com.shekharkg.githubpr.model.PullRequest
import com.squareup.picasso.Picasso

class PrViewHolder(private val binding: ItemPrBinding) : RecyclerView.ViewHolder(binding.root) {


    fun onBind(pullRequest: PullRequest) {
        binding.titleTV.text = pullRequest.title
        binding.createdAtTV.text = pullRequest.createdAt
        binding.closedAtTV.text = pullRequest.closedAt

        binding.userNameTV.text = pullRequest.user?.name
        Picasso.get().load(pullRequest.user?.avatar).into(binding.userImageIV)
    }

}