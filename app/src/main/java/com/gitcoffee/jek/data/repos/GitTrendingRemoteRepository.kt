package com.gitcoffee.jek.data.repos

import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import com.gitcoffee.jek.data.Constants
import com.gitcoffee.jek.domain.repo.GitTrendDataSource
import com.gitcoffee.jek.network.ArrayRequest
import com.gitcoffee.jek.network.NetworkCustomError
import com.gitcoffee.jek.network.Resource
import com.gitcoffee.jek.presentation.models.TrendingRepoItem
import javax.inject.Inject


open class GitTrendingRemoteRepository @Inject constructor() : GitTrendDataSource {


    override fun getTrendingRepos(resultList: MutableLiveData<Resource<List<TrendingRepoItem>>>) {
        val request = ArrayRequest(
            url = Constants.getGitTrendingRepoUrl(),
            responseType = TrendingRepoItem::class.java,
            errorListener = Response.ErrorListener {

                resultList.value = Resource.error(NetworkCustomError())

            },
            responseListener = Response.Listener {

                resultList.value = Resource.success(it)

            })

        request.setShouldCache(false)

        request.execute()

        resultList.value = Resource.loading()

    }


}