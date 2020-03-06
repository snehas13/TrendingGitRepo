package com.learn.gitrepo.framework.db

import android.content.Context
import com.learn.gitrepo.framework.model.GitRepoEntity

class RoomGitRepoDataSource(context: Context) : GitRepoDataSource {

    private val repoDao = GitRepoDatabase.getDatabase(context)!!.gitRepoDao()

    override suspend fun addAll(repoList: List<GitRepoEntity>) {
        return repoDao.insertAllRepo(repoList)
    }

    override suspend fun readAll(): List<GitRepoEntity> {
       return repoDao.getAllRepo()
    }
}