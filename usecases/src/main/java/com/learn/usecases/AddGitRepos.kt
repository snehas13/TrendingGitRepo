package com.learn.usecases

import com.learn.data.GitRepoRepository
import com.learn.domain.GitRepo

class AddGitRepos(private val gitRepository: GitRepoRepository) {
    suspend operator fun invoke(repoList: List<GitRepo>) =
       gitRepository.addAll(repoList)
}