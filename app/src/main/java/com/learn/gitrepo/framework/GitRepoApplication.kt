package com.learn.gitrepo.framework

import android.app.Application
import com.learn.gitrepo.framework.di.interactorsModule
import com.learn.gitrepo.framework.di.repoModule
import org.koin.android.ext.android.startKoin

class GitRepoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(repoModule, interactorsModule))
    }
}