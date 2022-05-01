package com.shekharkg.githubpr

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.shekharkg.githubpr.databinding.ActivityMainBinding
import com.shekharkg.githubpr.model.NetworkResult

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appContainer = (application as GitHubPR).appContainer
        viewModel = appContainer.getMainViewModel(this)

        addObserver()
    }

    private fun addObserver() {
        viewModel.getPullRequest().observe(this) {
            it?.let { prs ->
                Log.e(TAG, "PRS: pulled : ${prs.size}")
            }
        }


        viewModel.getNetworkState().observe(this) {
            when (it) {
                is NetworkResult.Success -> {

                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {

                }
            }
        }
    }
}