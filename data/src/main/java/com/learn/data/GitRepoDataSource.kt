package com.learn.data

import com.learn.domain.GitRepo

interface GitRepoDataSource {
    suspend fun addAll(repoList : List<GitRepo>)
    suspend fun readAll() : List<GitRepo>
    suspend fun fetchRepos(serverCallback : ServerCallback)
}