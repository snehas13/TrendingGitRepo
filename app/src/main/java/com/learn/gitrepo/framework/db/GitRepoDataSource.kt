package com.learn.gitrepo.framework.db

import com.learn.gitrepo.framework.model.GitRepoEntity

interface GitRepoDataSource {
    suspend fun addAll(repoList : List<GitRepoEntity>)
    suspend fun readAll() : List<GitRepoEntity>
}