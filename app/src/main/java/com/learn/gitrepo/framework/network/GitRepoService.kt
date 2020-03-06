package com.learn.gitrepo.framework.network

import com.learn.gitrepo.framework.model.GitRepoEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GitRepoService {

    @GET("/repositories")
    fun getRepoDetails(@Query("language") language : String?,
                       @Query("since") since : String?,
                       @Query("spoken_language_code") spoken_language_code : String?) : Call<GitRepoEntity>
}