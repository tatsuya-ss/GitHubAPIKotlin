package com.example.githubapikotlin

// 依存したクラス
data class GitHub(val login: String,
                  val name: String,
                  val location: String)

// 依存しないクラス
class GitHubEntity(val login: String,
                   val name: String,
                   val location: String)