package com.learn.gitrepo.presentation.interactors

import com.learn.usecases.AddGitRepos
import com.learn.usecases.GetGitRepos

data class Interactors(
    val addGitRepos: AddGitRepos,
    val getGitRepos: GetGitRepos
)