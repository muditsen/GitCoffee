package com.gitcoffee.jek.data.repos


import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.gitcoffee.jek.data.db.TrendingRepoDatabase
import com.gitcoffee.jek.domain.entities.TrendingRepoEntity
import com.gitcoffee.jek.network.NetworkCustomError
import com.gitcoffee.jek.network.Resource
import com.gitcoffee.jek.network.Status
import com.gitcoffee.jek.presentation.models.TrendingRepoItem
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GitTrendingLocalRepositoryTest {

    private lateinit var gitTrendingLocalRepository: GitTrendingLocalRepository

    private lateinit var liveData: MutableLiveData<Resource<List<TrendingRepoItem>>>

    private lateinit var db: TrendingRepoDatabase

    private val lockObject  = Object()


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, TrendingRepoDatabase::class.java).build()
        gitTrendingLocalRepository = GitTrendingLocalRepository(db)

        liveData = MutableLiveData()
    }

    @Test
    fun getTrendingRepos() {
        gitTrendingLocalRepository.getTrendingRepos(liveData)
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

    @Test
    fun insertTrendingReposIntoDatabase() {

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


        gitTrendingLocalRepository.insertTrendingReposIntoDatabase(list).invokeOnCompletion {
            Log.d("Test Data", "$numberOfItem, ${db.trendingRepoDao().getCount()}")
            assertEquals(numberOfItem, db.trendingRepoDao().getCount())
            synchronized(lockObject){
                lockObject.notify()
            }
        }

        synchronized(lockObject){
            lockObject.wait()
        }



    }

    @Test
    fun deleteAllRepo() {

        val list = ArrayList<TrendingRepoEntity>()

        val numberOfItem = 5

        for(i in 0 until numberOfItem){

            val trendingRepoEntity = TrendingRepoEntity(
                id = 0,
                author = "scds $i",
                name = "Mudit $i",
                avatar = "mudit $i",
                url = "http://github.com/muditsen $i",
                description = "Hey there this is testing desc. $i",
                language = "Java/kotlin $i",
                languageColor = "#343434",
                stars = 18+i,
                forks = 12+i,
                currentPeriodStars = 9+i
            )
            list.add(trendingRepoEntity)
            db.trendingRepoDao().insert(trendingRepoEntity)
        }

        assertEquals(numberOfItem,db.trendingRepoDao().getCount())

        gitTrendingLocalRepository.deleteAllRepo().invokeOnCompletion {
            assertEquals(0,db.trendingRepoDao().getCount())
            synchronized(lockObject){
                lockObject.notify()
            }
        }

        synchronized(lockObject){
            lockObject.wait()
        }




    }
}