package com.learn.gitrepo.framework.interactors

import com.learn.usecases.AddGitRepos
import com.learn.usecases.FetchGitRepos
import com.learn.usecases.GetGitRepos

data class Interactors(
    val addGitRepos: AddGitRepos,
    val getGitRepos: GetGitRepos,
    val fetchGitRepos: FetchGitRepos
)