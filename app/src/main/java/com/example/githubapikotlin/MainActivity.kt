package com.example.githubapikotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubapikotlin.databinding.ActivityMainBinding
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val gitHubUseCase = GitHubUseCaseImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupButton()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupButton() {
        binding.button.setOnClickListener {
            didTapButton()
        }
    }

    private fun didTapButton() {

        GlobalScope.launch {
            launch {
                val gitHub = gitHubUseCase.fetch()
                gitHub?.let {
                    println("debug, fetch")
                    binding.nameTextView.text = gitHub.name
                    binding.loginTextView.text = gitHub.login
                    binding.locationTextView.text = gitHub.location
                }
            }
        }

    }

}

