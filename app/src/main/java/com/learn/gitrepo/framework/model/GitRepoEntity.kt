package com.learn.gitrepo.framework.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "GitRepoEntity")
data class GitRepoEntity (
    @SerializedName("author")  @PrimaryKey var author : String,
    @SerializedName("name") var name : String?,
    @SerializedName("avatar") var avatar : String?,
    @SerializedName("url") var url : String?,
    @SerializedName("description") var description : String?,
    @SerializedName("language") var language : String?,
    @SerializedName("languageColor") var languageColor : String?,
    @SerializedName("stars") var stars : Int,
    @SerializedName("forks") var forks : Int,
    @SerializedName("currentPeriodStars") var currentPeriodStars : Int,
    @SerializedName("builtBy") var builtBy : List<BuiltByEntity>?
)