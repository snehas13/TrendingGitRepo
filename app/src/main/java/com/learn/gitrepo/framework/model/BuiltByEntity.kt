package com.learn.gitrepo.framework.model

import com.google.gson.annotations.SerializedName

data class BuiltByEntity (
    @SerializedName("href") val username : String,
    @SerializedName("avatar") val href : String,
    @SerializedName("username") val avatar : String
)