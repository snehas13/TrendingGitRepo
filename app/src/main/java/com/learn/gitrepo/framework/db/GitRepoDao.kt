package com.learn.gitrepo.framework.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.learn.gitrepo.framework.model.GitRepoEntity


@Dao
interface GitRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRepo(repoList : List<GitRepoEntity>)

    @Query("SELECT * FROM GitRepoEntity")
    fun getAllRepo(): List<GitRepoEntity>

}