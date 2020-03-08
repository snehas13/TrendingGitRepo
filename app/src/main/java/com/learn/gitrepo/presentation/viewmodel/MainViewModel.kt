package com.learn.gitrepo.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.data.ServerCallback
import com.learn.domain.GitRepo
import com.learn.gitrepo.framework.interactors.Interactors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.NetworkCapabilities
import android.os.Build
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Network


class MainViewModel : ViewModel(), KoinComponent {

    private val interactors: Interactors by inject()
    val repoLiveData: MutableLiveData<List<GitRepo>> = MutableLiveData()
    val TAG : String = "MainViewModel"

    fun fetchRepo(context: Context) {
       if(isInternetOn(context)) {
           Log.d(TAG,"Internet connection available")
           fetchDataFromServer()
       } else {
           Log.d(TAG,"No Internet connection available")
           repoLiveData.postValue(null)
       }

    }

    fun fetchDataFromServer() {

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                interactors.fetchGitRepos(object : ServerCallback {
                    override fun onError() {
                        Log.e(TAG,"Error downloading the Gitrepo")
                        repoLiveData.postValue(null)
                    }

                    override fun onRepoListLoaded(repoList: List<GitRepo>) {
                        GlobalScope.launch {
                            withContext(Dispatchers.IO) {
                                interactors.addGitRepos(repoList)
                            }
                            getRepos()
                        }

                    }
                })
            }
        }
    }

    fun getRepos() {
        GlobalScope.launch {
            repoLiveData.postValue(interactors.getGitRepos())
        }
    }

    private fun isInternetOn(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as
                    ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            postAndroidMInternetCheck(connectivityManager)
        } else {
            preAndroidMInternetCheck(connectivityManager)
        }
    }

    private fun preAndroidMInternetCheck(connectivityManager: ConnectivityManager): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (activeNetwork != null) {
            return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
        }
        return false
    }

    private fun postAndroidMInternetCheck(connectivityManager: ConnectivityManager): Boolean {
        val network = connectivityManager.activeNetwork
        val connection = connectivityManager.getNetworkCapabilities(network)

        return connection != null && (
                connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

}