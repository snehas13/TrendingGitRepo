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

class MainActivity : AppCompatActivity() {

    lateinit var repoAdapter: RepoAdapter
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        shimmerLayout.startShimmerAnimation()
        initView()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.repoLiveData.observe(this, Observer {
            if(shimmerLayout.visibility == View.VISIBLE) {
                shimmerLayout.stopShimmerAnimation()
                shimmerLayout.visibility = View.GONE
            }
            if(itemsToRefresh.isRefreshing) {
                itemsToRefresh.isRefreshing = false
            }
            if(it != null && it.isNotEmpty()) {
                viewErrorScreen(false)
                repoAdapter.update(it)
            } else {
                viewErrorScreen(true)
            }
        })

        viewModel.fetchRepo(this)

        retryButton.setOnClickListener {
            if(viewModel != null) {
                viewModel.fetchRepo(this)
            }
        }
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
            viewModel?.fetchRepo(this)
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
}
