package com.gitcoffee.jek.data.repos

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.gitcoffee.jek.data.Constants
import com.gitcoffee.jek.domain.repo.GitTrendDataSource
import com.gitcoffee.jek.network.Resource
import com.gitcoffee.jek.presentation.models.TrendingRepoItem
import javax.inject.Inject


open class GitTrendingRepository @Inject constructor(
    private val gitTrendingLocalRepository: GitTrendingLocalRepository,
    private val gitTrendingRemoteRepository: GitTrendingRemoteRepository,
    private val sharedPreferences:SharedPreferences
) : GitTrendDataSource {

    var forceFetchRemote: Boolean = false

    private var cacheExists: Boolean = false


    override fun getTrendingRepos(resultList: MutableLiveData<Resource<List<TrendingRepoItem>>>) {
        if(isToCallRemoteRepository()){
            gitTrendingRemoteRepository.getTrendingRepos(resultList)
        }else{
            gitTrendingLocalRepository.getTrendingRepos(resultList)
        }
    }

    fun insertTrendingRepo(list: List<TrendingRepoItem>) {
        cacheExists = true
        gitTrendingLocalRepository.insertTrendingReposIntoDatabase(list)
        sharedPreferences.edit().putBoolean(Constants.IS_CACHE_EXIST,true).putLong(Constants.TRENDING_DATA_UPDATE_TIME,System.currentTimeMillis()).apply()
    }

    fun deleteTrendingRepo() {
        sharedPreferences.edit().putBoolean(Constants.IS_CACHE_EXIST,false).apply()
        gitTrendingLocalRepository.deleteAllRepo()
    }

    //2hours elapsed
    private fun isTimeElapsed():Boolean{
        val timeNow = System.currentTimeMillis()
        val timeDiff = timeNow - sharedPreferences.getLong(Constants.TRENDING_DATA_UPDATE_TIME,System.currentTimeMillis())

        return (timeDiff / (1000 * 60 * 60)) > 2

    }

    fun isToCallRemoteRepository():Boolean{
        return forceFetchRemote || !sharedPreferences.getBoolean(Constants.IS_CACHE_EXIST,false) || isTimeElapsed()
    }
}