package com.example.githubapikotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubapikotlin.databinding.ActivityMainBinding
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
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
                print("debug, ${fetch()}") // 4
                println("debug, coroutine end!") // 5
            }
        }
        println("debug, stop!") // 2
    }

    // 中断するものにsuspendつける
    suspend fun fetch(): String {
        val urlString = "https://api.github.com/users/tatsuya-ss"
        val (_, _, result) = urlString.httpGet().responseString()
        return when(result) {
            is Result.Failure -> {
                result.getException().toString()
            }
            is Result.Success -> {
                result.get()
            }
        }
    }
}