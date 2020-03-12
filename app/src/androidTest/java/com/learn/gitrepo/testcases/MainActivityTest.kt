package com.learn.gitrepo.testcases

import androidx.test.rule.ActivityTestRule
import com.learn.gitrepo.presentation.view.MainActivity
import androidx.test.espresso.Espresso.onView
import android.content.Intent
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.learn.gitrepo.R
import com.learn.gitrepo.framework.network.GitRepoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.*
import org.junit.Rule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer


class MainActivityTest {

    val server = MockWebServer()
    val mockUrl = "https://private-anon-ff00b92047-githubtrendingapi.apiary-mock.com/"

    @Before
    fun setup() {
        server.start()
    }

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun serverSuccessCallback() {

        val jsonString = "[{\n" +
                "    \"author\": \"kusti8\",\n" +
                "    \"name\": \"proton-native\",\n" +
                "    \"avatar\": \"https://github.com/kusti8.png\",\n" +
                "    \"url\": \"https://github.com/kusti8/proton-native\",\n" +
                "    \"description\": \"A React environment for cross platform native desktop apps\",\n" +
                "    \"language\": \"JavaScript\",\n" +
                "    \"languageColor\": \"#3572A5\",\n" +
                "    \"stars\": 4711,\n" +
                "    \"forks\": 124,\n" +
                "    \"currentPeriodStars\": 1186,\n" +
                "    \"builtBy\": [{\n" +
                "        \"href\": \"https://github.com/viatsko\",\n" +
                "        \"avatar\": \"https://avatars0.githubusercontent.com/u/376065\",\n" +
                "        \"username\": \"viatsko\"\n" +
                "    }]\n" +
                "}]"

        val retrofit = Retrofit.Builder()
            .baseUrl(mockUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(GitRepoService::class.java)
        server.enqueue(MockResponse().setBody(jsonString))
        val call = service.getRepoDetails()
        val response = call.execute()

        val intent = Intent()
        mActivityTestRule.launchActivity(intent)

        Assert.assertTrue(response.isSuccessful)
        onView(withId(R.id.recyclerView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    }

    @Test
    fun serverFailureCallback() {

        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(GitRepoService::class.java)
        server.enqueue(MockResponse().setResponseCode(404))
        val call = service.getRepoDetails()
        val response = call.execute()

        val intent = Intent()
        mActivityTestRule.launchActivity(intent)

        Assert.assertTrue(!response.isSuccessful)
    }

    @After
    fun tearDown(){
        server.shutdown()
    }
}