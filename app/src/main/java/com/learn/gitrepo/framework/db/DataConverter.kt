package com.learn.gitrepo.framework.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.learn.gitrepo.framework.model.BuiltByEntity

class DataConverter {

    @TypeConverter
    fun fromBuitByList(value: List<BuiltByEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<BuiltByEntity>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toBuiltByList(value: String): List<BuiltByEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<BuiltByEntity>>() {}.type
        return gson.fromJson(value, type)
    }

}