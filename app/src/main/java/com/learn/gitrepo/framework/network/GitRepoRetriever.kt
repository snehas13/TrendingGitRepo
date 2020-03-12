package com.learn.gitrepo.framework.network

import com.learn.gitrepo.framework.model.GitRepoEntity
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class GitRepoRetriever {

    private val service: GitRepoService

    companion object {
        val BASE_URL = "https://github-trending-api.now.sh/"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        service = retrofit.create(GitRepoService::class.java)
    }


    fun getGitRepo(callback: Callback<List<GitRepoEntity>>) {
        val call  = service.getRepoDetails()
        call.enqueue(callback)
    }

}