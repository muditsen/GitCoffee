package com.gitcoffee.jek.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.gitcoffee.jek.data.repos.GitTrendingRepository
import com.gitcoffee.jek.network.NetworkCustomError
import com.gitcoffee.jek.network.Resource
import com.gitcoffee.jek.network.Status
import com.gitcoffee.jek.presentation.models.TrendingRepoItem
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito

class TrendingViewModelTest {

    private lateinit var liveData: MutableLiveData<Resource<List<TrendingRepoItem>>>

    @Mock
    private lateinit var gitTrendingRepository: GitTrendingRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        liveData = MutableLiveData()
    }

    @Test
    fun getTrendingRepo() {

        liveData.observeForever {

            when (it.status) {
                Status.SUCCESS -> {
                    assertNotNull(gitTrendingRepository)
                    assertNotNull(it)
                    assertNotNull(it.data)
                    gitTrendingRepository.deleteTrendingRepo()
                    gitTrendingRepository.insertTrendingRepo(it.data!!)
                }
                else -> {

                }
            }
        }

        mockViewModel()


    }

    private fun mockViewModel() {
        Mockito.`when`(gitTrendingRepository.getTrendingRepos(liveData)).then {


            liveData.value = Resource.loading()


            //Success
            Thread.sleep(3000)


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

            liveData.value = Resource.success(list)


            //Error
            Any()
        }
    }

    @Test
    fun fetchTrendingRepo() {
        mockViewModel()
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
    }
}