package com.shekharkg.githubpr.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shekharkg.githubpr.databinding.ItemPrBinding
import com.shekharkg.githubpr.model.PullRequest

class PrAdapter : RecyclerView.Adapter<PrViewHolder>() {

    private var pullRequest: List<PullRequest> = ArrayList()

    fun setPullRequest(prs: List<PullRequest>) {
        this.pullRequest = prs
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrViewHolder =

        PrViewHolder(
            ItemPrBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PrViewHolder, position: Int) {
        holder.onBind(pullRequest[position])
    }

    override fun getItemCount(): Int {
        return pullRequest.size
    }
}