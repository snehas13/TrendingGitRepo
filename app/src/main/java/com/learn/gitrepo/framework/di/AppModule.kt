package com.learn.gitrepo.framework.di

import com.learn.data.GitRepoRepository
import com.learn.gitrepo.framework.interactors.GitRepoDataSourceImpl
import com.learn.gitrepo.framework.interactors.Interactors
import com.learn.usecases.AddGitRepos
import com.learn.usecases.FetchGitRepos
import com.learn.usecases.GetGitRepos
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val repoModule = module {
    single {
        GitRepoRepository(GitRepoDataSourceImpl(androidContext()))
    }
}

val interactorsModule = module {
    single<Interactors> {
        Interactors(
            AddGitRepos(gitRepository = get()),
            GetGitRepos(gitRepository = get()),
            FetchGitRepos(gitRepository = get())
        )
    }
}