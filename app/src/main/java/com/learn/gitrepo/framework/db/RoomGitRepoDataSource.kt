package com.learn.gitrepo.framework.db

import android.content.Context
import com.learn.data.GitRepoDataSource
import com.learn.domain.BuiltBy
import com.learn.domain.GitRepo
import com.learn.gitrepo.framework.model.BuiltByEntity
import com.learn.gitrepo.framework.model.GitRepoEntity

class RoomGitRepoDataSource(context: Context) : GitRepoDataSource {

    private val repoDao = GitRepoDatabase.getDatabase(context)!!.gitRepoDao()

    override suspend fun addAll(repoList: List<GitRepo>) {
        return repoDao.insertAllRepo(repoList.map {
            GitRepoEntity(it.author, it.name ,it.avatar, it.url,
                it.description, it.language, it.languageColor, it.stars,
                it.forks, it.currentPeriodStars,
                it.builtBy.map { builtBy -> BuiltByEntity (builtBy.username, builtBy.href, builtBy.avatar) })
        })
    }

    override suspend fun readAll(): List<GitRepo> {
       return repoDao.getAllRepo().map {
           GitRepo(it.author, it.name ,it.avatar, it.url,
               it.description, it.language, it.languageColor, it.stars,
               it.forks, it.currentPeriodStars,
               it.builtBy.map { builtBy -> BuiltBy(builtBy.username, builtBy.href, builtBy.avatar) }) }
    }
}