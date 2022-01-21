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
            main()
        }
    }

    private fun main() {
        println("debug, start!")  // 1
        GlobalScope.launch {
            launch {
                println("debug, coroutine start!") // 3
                println("debug, ${fetch()}") // 4
                val gitHub = fetch()
                binding.nameTextView.text = gitHub.name
                binding.loginTextView.text = gitHub.login
                binding.locationTextView.text = gitHub.location
                println("debug, coroutine end!") // 5
            }
        }
        println("debug, stop!") // 2
    }

    // 中断するものにsuspendつける
    suspend fun fetch(): GitHubModel {
        val urlString = "https://api.github.com/users/tatsuya-ss"
        val (_, _, result) = urlString.httpGet().responseString()
        return when(result) {
            is Result.Failure -> {
                println(result.getException().toString())
                return GitHubModel("不明", "不明", "不明")
            }
            is Result.Success -> {
                val jsonResult = result.get()
                var gitHub = Gson().fromJson(jsonResult, GitHubModel::class.java)
                return gitHub
            }
        }
    }
}

data class GitHubModel(val login: String,
                       val name: String,
                       val location: String)