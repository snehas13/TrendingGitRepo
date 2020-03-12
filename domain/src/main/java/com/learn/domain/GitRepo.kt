package com.learn.domain

data class GitRepo(
    var author : String,
    var name : String?,
    var avatar : String?,
    var url : String?,
    var description : String?,
    var language : String?,
    var languageColor : String?,
    val stars : Int,
    val forks : Int,
    val currentPeriodStars : Int,
    val builtBy : List<BuiltBy>?

)
