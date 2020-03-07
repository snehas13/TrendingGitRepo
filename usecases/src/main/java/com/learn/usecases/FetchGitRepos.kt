package com.learn.usecases

import com.learn.data.GitRepoRepository
import com.learn.data.ServerCallback

class FetchGitRepos(private val gitRepository: GitRepoRepository) {

    suspend operator fun invoke(serverCallback: ServerCallback) = gitRepository.fetchRepos(serverCallback)
}