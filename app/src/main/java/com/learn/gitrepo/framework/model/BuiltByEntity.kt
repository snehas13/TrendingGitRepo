package com.learn.gitrepo.framework.model

import com.google.gson.annotations.SerializedName

data class BuiltByEntity (
    @SerializedName("href") var username : String?,
    @SerializedName("avatar") var href : String?,
    @SerializedName("username") var avatar : String?
)