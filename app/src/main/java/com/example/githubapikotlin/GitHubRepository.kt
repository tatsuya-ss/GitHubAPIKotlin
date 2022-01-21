package com.example.githubapikotlin

import com.google.gson.Gson

interface GitHubRepository {
    fun fetch(): GitHubEntity?
}

class GitHubRepositoryImpl(private val dataStore: GitHubDataStore = GitHubDataStoreImple()): GitHubRepository {

    override fun fetch(): GitHubEntity? {
        val result = dataStore.fetch()
        when(result) {
            null -> { return null }
            else -> {
                val github = Gson().fromJson(result, GitHub::class.java)
                return convertToGitHubEntity(github)
            }
        }
    }

}

private fun GitHubRepositoryImpl.convertToGitHubEntity(dataStore: GitHub): GitHubEntity {
    return GitHubEntity(dataStore.login, dataStore.name, dataStore.location)
}