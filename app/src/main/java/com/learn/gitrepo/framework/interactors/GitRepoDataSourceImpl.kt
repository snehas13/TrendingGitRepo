package com.learn.gitrepo.framework.interactors

import android.content.Context
import com.learn.data.GitRepoDataSource
import com.learn.data.ServerCallback
import com.learn.domain.BuiltBy
import com.learn.domain.GitRepo
import com.learn.gitrepo.framework.db.GitRepoDatabase
import com.learn.gitrepo.framework.model.BuiltByEntity
import com.learn.gitrepo.framework.model.GitRepoEntity
import com.learn.gitrepo.framework.network.GitRepoRetriever
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitRepoDataSourceImpl(context: Context) : GitRepoDataSource {

    val gitRepoRetriever = GitRepoRetriever()

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


    override suspend fun fetchRepos(serverCallback: ServerCallback) {
        gitRepoRetriever.getGitRepo(object : Callback<GitRepoEntity> {
            override fun onFailure(call: Call<GitRepoEntity>?, t: Throwable?) {
                serverCallback.onError()
            }

            override fun onResponse(call: Call<GitRepoEntity>?, response: Response<GitRepoEntity>?) {
                val repoList = response!!.body()!! as List<GitRepoEntity>
                if(repoList.isNotEmpty()) {
                    serverCallback.onRepoListLoaded(
                        repoList.map {
                            GitRepo(it.author, it.name ,it.avatar, it.url,
                                it.description, it.language, it.languageColor, it.stars,
                                it.forks, it.currentPeriodStars,
                                it.builtBy.map { builtBy -> BuiltBy(builtBy.username, builtBy.href, builtBy.avatar) })
                        }
                    )
                } else {
                    serverCallback.onError()
                }
            }
        })
    }
}