package com.shekharkg.githubpr

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.shekharkg.githubpr.adapter.PrAdapter
import com.shekharkg.githubpr.databinding.ActivityMainBinding
import com.shekharkg.githubpr.model.NetworkResult

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PrAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appContainer = (application as GitHubPR).appContainer
        viewModel = appContainer.getMainViewModel(this)

        setupView()
        addObserver()
    }

    private fun setupView() {
        adapter = PrAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding.prRV.layoutManager = layoutManager
        binding.prRV.adapter = adapter

        binding.actionRetry.setOnClickListener {
            viewModel.fetchRepositories(GitHubUser)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addObserver() {
        viewModel.getPullRequest().observe(this) {
            it?.let { prs ->
                adapter.setPullRequest(prs)
                adapter.notifyDataSetChanged()
            }
        }


        viewModel.getNetworkState().observe(this) {
            when (it) {
                is NetworkResult.Success -> if (!viewModel.isApiCallInProgress()) setupViewForSuccessState()
                is NetworkResult.Loading -> setupViewForLoadingState()
                is NetworkResult.Error -> if (!viewModel.isApiCallInProgress()) setupViewForFailureState(
                    it.message
                )
            }
        }
    }

    private fun setupViewForSuccessState() {
        binding.topLoader.visibility = View.GONE
        binding.centerLoader.visibility = View.GONE
        if (adapter.itemCount > 0) {
            binding.errorView.visibility = View.GONE
        } else {
            binding.errorTV.text =
                "No Closed Pull Request found in Public repository for $GitHubUser"
            binding.errorView.visibility = View.VISIBLE
            binding.actionRetry.visibility = View.GONE
        }
    }

    private fun setupViewForLoadingState() {
        if (adapter.itemCount > 0) {
            binding.topLoader.visibility = View.VISIBLE
            binding.centerLoader.visibility = View.GONE
        } else {
            binding.topLoader.visibility = View.GONE
            binding.centerLoader.visibility = View.VISIBLE
        }
        binding.errorView.visibility = View.GONE
    }

    private fun setupViewForFailureState(message: String?) {
        binding.topLoader.visibility = View.GONE
        binding.centerLoader.visibility = View.GONE

        if (adapter.itemCount > 0) {
            Toast.makeText(this, message ?: "Unable to fetch all PRs", Toast.LENGTH_LONG).show()
        } else {
            binding.errorView.visibility = View.VISIBLE
            binding.errorTV.text = message ?: "Something went wrong!"
        }

    }
}