package com.gitcoffee.jek.data.repos

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import com.android.volley.Response
import com.gitcoffee.jek.data.Constants
import com.gitcoffee.jek.network.*
import com.gitcoffee.jek.presentation.common.AppUtility
import com.gitcoffee.jek.presentation.models.TrendingRepoItem
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class GitTrendingRemoteRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var gitTrendingRemoteRepository: GitTrendingRemoteRepository

    private lateinit var liveData: MutableLiveData<Resource<List<TrendingRepoItem>>>

    private val lockObject  = Object()

    @Before
    fun setUp() {
        VolleySingleton.init(ApplicationProvider.getApplicationContext<Context>())
        gitTrendingRemoteRepository = GitTrendingRemoteRepository()

        liveData = MutableLiveData()

    }

    @Test
    fun getTrendingRepos() {

        liveData.observeForever {
            when (it.status) {
                Status.SUCCESS -> {
                    assertNotNull(it.data)
                    assertNull(it.error)
                    assert(it.data is List<TrendingRepoItem>)
                }

                Status.LOADING -> {
                    assertNull(it.data)
                    assertNull(it.error)
                }

                Status.ERROR -> {
                    assertNull(it.data)
                    assertNotNull(it.error)
                    assert(it.error is NetworkCustomError)
                    assertNotNull(it.error?.msg)
                }
            }
        }

        val request = ArrayRequest(
            url = Constants.getGitTrendingRepoUrl(),
            responseType = TrendingRepoItem::class.java,
            errorListener = Response.ErrorListener {
                liveData.value = Resource.error(AppUtility.getNetworkError(it))
                resumeThread()

            },
            responseListener = Response.Listener {
                liveData.value = Resource.success(it)
                resumeThread()

            })


        liveData.value = Resource.loading()
        request.execute()
        stopThread()
    }

    private fun stopThread(){
        synchronized(lockObject){
            lockObject.wait()
        }
    }

    private fun resumeThread(){
        synchronized(lockObject){
            lockObject.notify()
        }
    }
}