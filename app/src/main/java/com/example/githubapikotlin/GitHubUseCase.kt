package com.example.githubapikotlin

interface GitHubUseCase {
    fun fetch(): GitHubEntity?
}

open class GitHubUseCaseImpl(private val repository: GitHubRepository = GitHubRepositoryImpl()): GitHubUseCase {

    override fun fetch(): GitHubEntity? {
        return repository.fetch()
    }

}