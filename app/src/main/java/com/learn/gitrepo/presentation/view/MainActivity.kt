package com.learn.gitrepo.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.gitrepo.R
import com.learn.gitrepo.presentation.adapters.RepoAdapter
import com.learn.gitrepo.presentation.viewmodel.MainViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import androidx.work.*
import com.learn.gitrepo.framework.bgtask.DownloadWorker
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var repoAdapter: RepoAdapter
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initView()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if(!viewModel.isActivityInited) {
            startAnimation()
            viewModel.fetchRepo()
        } else {
            viewModel.getRepos()
        }

        viewModel.repoLiveData.observe(this, Observer {

            stopAnimation()

            if(itemsToRefresh.isRefreshing) {
                itemsToRefresh.isRefreshing = false
            }

            if(it != null && it.isNotEmpty()) {
                viewErrorScreen(false)
                viewModel.isActivityInited = true
                repoAdapter.update(it)
            } else {
                viewErrorScreen(true)
            }
        })

        retryButton.setOnClickListener {
            no_connection_layout.visibility = View.GONE
            startAnimation()
            viewModel.fetchRepo()
        }

        initWorkManagerTask()
    }

    fun stopAnimation() {
        if(shimmerLayout.visibility == View.VISIBLE) {
            shimmerLayout.stopShimmerAnimation()
            shimmerLayout.visibility = View.GONE
        }
    }

    fun startAnimation() {
        shimmerLayout.visibility = View.VISIBLE
        shimmerLayout.startShimmerAnimation()
    }

    fun initWorkManagerTask() {
        val sendDataBuilder = PeriodicWorkRequest.Builder(
            DownloadWorker::class.java,
            2,
            TimeUnit.HOURS
        ).setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
        val periodicWorkRequest = sendDataBuilder
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DownloadWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    fun viewErrorScreen(status: Boolean) {
        if(status) {
            recyclerView.visibility = View.GONE
            no_connection_layout.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            no_connection_layout.visibility = View.GONE
        }
    }

    fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        repoAdapter = RepoAdapter()
        recyclerView.adapter = repoAdapter

        itemsToRefresh.setOnRefreshListener {
            viewModel.fetchRepo()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAnimation()

    }
}
