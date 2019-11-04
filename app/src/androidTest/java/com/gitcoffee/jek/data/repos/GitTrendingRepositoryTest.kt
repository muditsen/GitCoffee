package com.gitcoffee.jek.data.repos

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import com.gitcoffee.jek.data.Constants
import com.gitcoffee.jek.network.Resource
import com.gitcoffee.jek.presentation.models.TrendingRepoItem
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class GitTrendingRepositoryTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var gitTrendingRepository: GitTrendingRepository

    @Mock
    private lateinit var gitTrendingLocalRepository: GitTrendingLocalRepository

    @Mock
    private lateinit var gitTrendingRemoteRepository: GitTrendingRemoteRepository

    @Mock
    private lateinit var liveData: MutableLiveData<Resource<List<TrendingRepoItem>>>

    private lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        gitTrendingRepository = GitTrendingRepository(
            gitTrendingLocalRepository,
            gitTrendingRemoteRepository,
            sharedPreferences
        )
    }


    @Test
    fun getTrendingRepos() {

        if(gitTrendingRepository.isToCallRemoteRepository()){
            gitTrendingRemoteRepository.getTrendingRepos(liveData)
            //Method call tested in Git trending remote repository. Please check GitTrendingRemoteRepositoryTest
        }else{
            gitTrendingLocalRepository.getTrendingRepos(liveData)
            //Method call tested in Git trending remote repository. Please check GitTrendingLocalRepositoryTest
        }

    }

    @Test fun isToCallRemoteRepository(){
        gitTrendingRepository.forceFetchRemote = true
        assert(gitTrendingRepository.isToCallRemoteRepository())

        gitTrendingRepository.forceFetchRemote = false
        sharedPreferences.edit().putBoolean(Constants.IS_CACHE_ENABLE,true).apply()
        assert(gitTrendingRepository.isToCallRemoteRepository())

        gitTrendingRepository.forceFetchRemote = false
        sharedPreferences.edit().putBoolean(Constants.IS_CACHE_ENABLE,true).apply()
        assert(gitTrendingRepository.isToCallRemoteRepository())

        gitTrendingRepository.forceFetchRemote = false
        sharedPreferences.edit().putBoolean(Constants.IS_CACHE_ENABLE,true)
        sharedPreferences.edit().putLong(Constants.TRENDING_DATA_UPDATE_TIME,1571799600000).apply()
        assert(gitTrendingRepository.isToCallRemoteRepository())


        gitTrendingRepository.forceFetchRemote = false
        sharedPreferences.edit().putBoolean(Constants.IS_CACHE_ENABLE,true)
        sharedPreferences.edit().putLong(Constants.TRENDING_DATA_UPDATE_TIME,System.currentTimeMillis() - (1000 * 60 * 60)).apply()
        assert(!gitTrendingRepository.isToCallRemoteRepository())

    }

    @Test
    //As shared prefs and local repository are unit tested this method is ought to work.
    fun insertTrendingRepo() {
        val list = ArrayList<TrendingRepoItem>()

        val numberOfItem = 5
        for (i in 0 until numberOfItem) {

            val trendingRepoEntity = TrendingRepoItem(
                author = "scds $i",
                name = "Mudit $i",
                avatar = "mudit $i",
                url = "http://github.com/muditsen $i",
                description = "Hey there this is testing desc. $i",
                language = "Java/kotlin $i",
                languageColor = "#343434",
                stars = 18 + i,
                forks = 12 + i,
                currentPeriodStars = 9 + i,
                builtBy = ArrayList()
            )
            list.add(trendingRepoEntity)
        }
       /* gitTrendingLocalRepository.insertTrendingReposIntoDatabase(list).invokeOnCompletion {

        }*/

    }

    @Test
    //As shared prefs and local repository are unit tested this method is ought to work.
    fun deleteTrendingRepo() {

    }

    @Test
    fun isTimeElapsed() {
        val timeNow = System.currentTimeMillis()
        val timeDiff = timeNow - 1571799600000 //time when writing this test case
        assert((timeDiff / (1000 * 60 * 60)) > 2)

        val timeb41Hour = timeNow - (1000 * 60 * 60)
        val timeDiff2 = timeNow - timeb41Hour
        assert((timeDiff2 / (1000 * 60 * 60)) < 2)
    }
}