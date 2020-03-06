package com.learn.usecases

import com.learn.data.GitRepoRepository

class GetGitRepos(private val gitRepository: GitRepoRepository) {
    suspend operator fun invoke() = gitRepository.readAll()
}