package com.learn.gitrepo.framework.network

import com.learn.gitrepo.framework.model.GitRepoEntity
import retrofit2.Call
import retrofit2.http.GET

interface GitRepoService {

    @GET("/repositories")
    fun getRepoDetails(): Call<List<GitRepoEntity>>
}