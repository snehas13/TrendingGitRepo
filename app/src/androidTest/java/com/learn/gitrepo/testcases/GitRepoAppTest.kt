package com.learn.gitrepo.testcases

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import com.learn.gitrepo.framework.db.GitRepoDao
import com.learn.gitrepo.framework.db.GitRepoDatabase
import com.learn.gitrepo.framework.model.BuiltByEntity
import com.learn.gitrepo.framework.model.GitRepoEntity
import com.learn.gitrepo.framework.network.GitRepoService
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class GitRepoAppTest {

    private lateinit var database: GitRepoDatabase
    private lateinit var gitRepoDao: GitRepoDao

    @Before
    fun setup() {

        val context: Context = InstrumentationRegistry.getInstrumentation().context
        try {
            database = Room.inMemoryDatabaseBuilder(context, GitRepoDatabase::class.java)
                .allowMainThreadQueries().build()
        } catch (e: Exception) {
            Log.i("GitRepoAppTest", e.message)
        }
        gitRepoDao = database.gitRepoDao()

    }

    @After
    fun tearDown() {
        database.close()
    }

    private fun getRepoEntity() : GitRepoEntity {

        val builtByList : List<BuiltByEntity> = listOf(
            BuiltByEntity("https://github.com/viatsko","https://avatars0.githubusercontent.com/u/376065","viatsko")
        )

        return GitRepoEntity(
            "author_test",
            "test",
            "https://github.com/google.png",
            "https://github.com/google/gvisor",
            "Container Runtime Sandbox",
            "Go",
            "#3572A5",
            3346,
            118,
            1624,
            builtByList
            )
    }

    @Test
    fun testAddingAndRetrievingData() {

        val repoEntity = getRepoEntity()

        // insert test repo into database
        gitRepoDao.insertAllRepo(listOf(repoEntity))

        // check whether the item has been inserted or not
        val repoList  = gitRepoDao.getAllRepo()

        assertTrue(repoList.isNotEmpty())

        assertTrue(repoList.contains(repoEntity))

    }

    @Test
    fun severCallWithSuccessful() {
        val url = "https://private-anon-31803d1f64-githubtrendingapi.apiary-mock.com/repositories/"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(GitRepoService::class.java)
        val call = service.getRepoDetails()
        val response : Response<List<GitRepoEntity>> = call.execute()
        assertTrue(response.isSuccessful)
        val repoList = response.body()!!
        assertTrue(repoList.isNotEmpty())
    }

}