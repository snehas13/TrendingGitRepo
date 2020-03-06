package com.learn.gitrepo.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.learn.data.model.GitRepo

@Database(entities=[GitRepo::class] , version=1 , exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class GitRepoDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "repo.db"

        private var instance : GitRepoDatabase? =null

        fun getDatabase(context : Context) : GitRepoDatabase? {
            if(instance == null) {
                instance = Room.databaseBuilder(context, GitRepoDatabase::class.java, DATABASE_NAME).build()
            }
            return instance
        }

    }

    abstract fun gitRepoDao() : GitRepoDao
}