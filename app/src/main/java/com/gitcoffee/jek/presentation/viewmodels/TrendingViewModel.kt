package com.gitcoffee.jek.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.gitcoffee.jek.network.*
import com.gitcoffee.jek.presentation.TrendingRepoItem

class TrendingViewModel : ViewModel() {

    private val resultList: MutableLiveData<Resource<List<TrendingRepoItem>>> = MutableLiveData()

    fun getTrendingRepo(): LiveData<Resource<List<TrendingRepoItem>>> {
        return Transformations.map(resultList) {
            when (it.status) {
                Status.SUCCESS -> {
                    val list = ArrayList<TrendingRepoItem>()
                    val size = it.data?.size ?: 0
                    val data = it.data
                    if (data != null) {
                        for (i in 0..size) {
                            list.add(data[i])
                        }

                        Resource.success<List<TrendingRepoItem>>(list)
                    } else {
                        Resource.error(
                            NetworkCustomError(
                                "List Is Empty"
                            )
                        )
                    }

                }

                Status.LOADING -> {
                    Resource.loading()
                }

                Status.ERROR -> {
                    Resource.error(it.error)
                }
            }
        }
    }

    public fun fetchTrendingRepo() {

        resultList.value = Resource.loading()

        val request = BaseListRequest(
            url = "https://github-trending-api.now.sh/repositories",
            responseType = TrendingRepoItem::class.java,
            errorListener = Response.ErrorListener {

                resultList.value = Resource.error(NetworkCustomError())

            },
            responseListener = Response.Listener {

                resultList.value = Resource.success(it)

            })

        request.execute()

    }
}