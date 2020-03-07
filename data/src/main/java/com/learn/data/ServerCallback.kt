package com.learn.data

import com.learn.domain.GitRepo

interface ServerCallback {
    fun onRepoListLoaded(repoList: List<GitRepo>)
    fun onError()
}