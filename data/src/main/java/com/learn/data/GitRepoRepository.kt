package com.learn.data

import com.learn.domain.GitRepo

class GitRepoRepository(private val dataSource: GitRepoDataSource) {
    suspend fun addAll(repoList : List<GitRepo>) =
        dataSource.addAll(repoList)

    suspend fun readAll() : List<GitRepo> =
        dataSource.readAll()

    suspend fun fetchRepos(serverCallback: ServerCallback)  =
        dataSource.fetchRepos(serverCallback)
}