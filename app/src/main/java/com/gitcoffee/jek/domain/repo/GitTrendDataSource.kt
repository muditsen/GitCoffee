package com.gitcoffee.jek.domain.repo

import androidx.lifecycle.MutableLiveData
import com.gitcoffee.jek.network.Resource
import com.gitcoffee.jek.presentation.models.TrendingRepoItem

interface GitTrendDataSource {

    fun getTrendingRepos(resultList: MutableLiveData<Resource<List<TrendingRepoItem>>>)

}