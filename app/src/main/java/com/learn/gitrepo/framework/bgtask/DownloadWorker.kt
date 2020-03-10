package com.learn.gitrepo.framework.bgtask

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.learn.data.ServerCallback
import com.learn.domain.GitRepo
import com.learn.gitrepo.framework.interactors.Interactors
import kotlinx.coroutines.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class DownloadWorker(context : Context, workerParams : WorkerParameters) : CoroutineWorker(context, workerParams), KoinComponent {

    val interactors :  Interactors by inject()
    val TAG : String = "DownloadWorker"

    override suspend fun doWork(): Result = coroutineScope {
        val jobs = async {

            Log.e(TAG,"Executing work manager: doWork()")
            interactors.fetchGitRepos(object : ServerCallback {
                override fun onError() {
                    Log.e(TAG,"Error downloading the Gitrepo")
                }

                override fun onRepoListLoaded(repoList: List<GitRepo>) {
                    GlobalScope.launch {
                        withContext(Dispatchers.IO) {
                            interactors.addGitRepos(repoList)
                        }
                    }

                }
            })
        }

        jobs.await()
        Result.success()
    }
}