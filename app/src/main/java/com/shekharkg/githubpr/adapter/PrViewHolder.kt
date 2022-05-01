package com.shekharkg.githubpr.adapter

import androidx.recyclerview.widget.RecyclerView
import com.shekharkg.githubpr.databinding.ItemPrBinding
import com.shekharkg.githubpr.model.PullRequest

class PrViewHolder(private val binding: ItemPrBinding) : RecyclerView.ViewHolder(binding.root) {


    fun onBind(pullRequest: PullRequest) {
        binding.titleTV.text = pullRequest.title
        binding.createdAtTV.text = pullRequest.created_at
        binding.closedAtTV.text = pullRequest.closed_at

        binding.userNameTV.text = pullRequest.user?.login

    }

}