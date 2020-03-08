package com.learn.gitrepo.framework.interactors

import android.content.Context
import android.util.Log
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
    val TAG = "GitRepoDataSourceImpl"

    private val repoDao = GitRepoDatabase.getDatabase(context)!!.gitRepoDao()

    override suspend fun addAll(repoList: List<GitRepo>) {
        val repoList =  ObjectMapper.mapDomainToEntity(repoList)
        return repoDao.insertAllRepo(repoList)
    }

    override suspend fun readAll(): List<GitRepo> {
        return ObjectMapper.mapEntityToDomain(repoDao.getAllRepo())
    }


    override suspend fun fetchRepos(serverCallback: ServerCallback) {
        gitRepoRetriever.getGitRepo(object : Callback<List<GitRepoEntity>> {
            override fun onFailure(call: Call<List<GitRepoEntity>>?, t: Throwable?) {
                Log.e(TAG,"error downloading json ${t!!.message} and url is $call")
                serverCallback.onError()
            }

            override fun onResponse(call: Call<List<GitRepoEntity>>?, response: Response<List<GitRepoEntity>>?) {
                val repoList = response!!.body()!!
                Log.e(TAG,"Response recieved $repoList")
                if(repoList.isNotEmpty()) {
                    serverCallback.onRepoListLoaded(
                        ObjectMapper.mapEntityToDomain(repoList)
                    )
                } else {
                    serverCallback.onError()
                }
            }
        })
    }
}