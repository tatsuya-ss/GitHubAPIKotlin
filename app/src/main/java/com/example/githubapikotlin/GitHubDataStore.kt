package com.example.githubapikotlin

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson

interface GitHubDataStore {
    fun fetch(): String?
}

class GitHubDataStoreImple: GitHubDataStore {

    override fun fetch(): String? {
        val urlString = "https://api.github.com/users/tatsuya-ss"
        val (_, _, result) = urlString.httpGet().responseString()
        return when(result) {
            is Result.Failure -> {
                println(result.getException().toString())
                return null
            }
            is Result.Success -> {
                val jsonResult = result.get()
                return jsonResult
            }
        }
    }

}