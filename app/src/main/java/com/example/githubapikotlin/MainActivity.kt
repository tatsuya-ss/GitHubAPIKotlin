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
        GlobalScope.launch {
            launch {
                val gitHub = fetch()
                gitHub?.let {
                    binding.nameTextView.text = gitHub.name
                    binding.loginTextView.text = gitHub.login
                    binding.locationTextView.text = gitHub.location
                }
            }
        }
    }

    // 中断するものにsuspendつける
    suspend fun fetch(): GitHub? {
        val urlString = "https://api.github.com/users/tatsuya-ss"
        val (_, _, result) = urlString.httpGet().responseString()
        return when(result) {
            is Result.Failure -> {
                println(result.getException().toString())
                return null
            }
            is Result.Success -> {
                val jsonResult = result.get()
                var gitHub = Gson().fromJson(jsonResult, GitHub::class.java)
                return gitHub
            }
        }
    }

}

