package com.gitcoffee.jek.data.repos

import androidx.lifecycle.MutableLiveData
import com.gitcoffee.jek.data.db.TrendingRepoDatabase
import com.gitcoffee.jek.domain.entities.TrendingRepoEntity
import com.gitcoffee.jek.domain.repo.GitTrendDataSource
import com.gitcoffee.jek.network.NetworkCustomError
import com.gitcoffee.jek.network.Resource
import com.gitcoffee.jek.presentation.models.TrendingRepoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class GitTrendingLocalRepository @Inject constructor(private val db: TrendingRepoDatabase) :
    GitTrendDataSource {

    private val uiScope = CoroutineScope(Dispatchers.Main)

    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun getTrendingRepos(resultList: MutableLiveData<Resource<List<TrendingRepoItem>>>){

        resultList.value = Resource.loading()

        uiScope.launch {
            val list = mapDbData()
            if(list.isEmpty()){
                resultList.value = Resource.error(NetworkCustomError("Db is empty. Try calling remote."))
            }else{
                resultList.value = Resource.success(list)
            }

        }

    }


    private suspend fun mapDbData() = withContext(Dispatchers.IO){
        val list = java.util.ArrayList<TrendingRepoItem>()
        for (i in db.trendingRepoDao().getAllTrendingRepos()) {
            list.add(
                TrendingRepoItem(
                    i.author,
                    i.name,
                    i.avatar,
                    i.url,
                    i.description,
                    i.language,
                    i.languageColor,
                    i.stars,
                    i.forks,
                    i.currentPeriodStars,
                    ArrayList()
                )
            )
        }
        list
    }

    fun insertTrendingReposIntoDatabase(list: List<TrendingRepoItem>)  = ioScope.launch{
        for (i in list) {
            db.trendingRepoDao().insert(
                TrendingRepoEntity(
                    0,
                    i.author,
                    i.name,
                    i.avatar,
                    i.url,
                    i.description,
                    i.language,
                    i.languageColor,
                    i.stars,
                    i.forks,
                    i.currentPeriodStars
                )
            )
        }
    }

    fun deleteAllRepo() = ioScope.launch{
        db.trendingRepoDao().deleteAllEntries()
    }


}