package com.gitcoffee.jek.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gitcoffee.jek.data.repos.GitTrendingRepository
import com.gitcoffee.jek.network.Resource
import com.gitcoffee.jek.network.Status
import com.gitcoffee.jek.presentation.models.TrendingRepoItem
import javax.inject.Inject

open class TrendingViewModel @Inject constructor(private val repository: GitTrendingRepository) :
    ViewModel() {

    private var trendingLiveData: LiveData<Resource<List<TrendingRepoItem>>> = MutableLiveData()

    private var trendingResponseReceived = false

    fun getTrendingRepoData(): LiveData<Resource<List<TrendingRepoItem>>> {
        return Transformations.map(trendingLiveData) {
            if (it.status == Status.SUCCESS) {
                repository.deleteTrendingRepo()
                repository.insertTrendingRepo(it.data!!)
                trendingResponseReceived = true
            }

            repository.forceFetchRemote = false

            it
        }
    }

    fun fetchTrendingRepo(force: Boolean = false) {
        if (force) {
            repository.forceFetchRemote = true
        }
        repository.getTrendingRepos(trendingLiveData as MutableLiveData<Resource<List<TrendingRepoItem>>>)

    }


}