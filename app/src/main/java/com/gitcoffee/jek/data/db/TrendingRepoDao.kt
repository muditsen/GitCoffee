package com.gitcoffee.jek.data.db

import androidx.room.*
import com.gitcoffee.jek.domain.entities.TrendingRepoEntity

@Dao
interface TrendingRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trendingRepoEntity: TrendingRepoEntity): Long

    @Update
    fun update(trendingRepoEntity: TrendingRepoEntity)

    @Delete
    fun delete(trendingRepoEntity: TrendingRepoEntity)

    @Query("Delete from Trending_repo_table")
    fun deleteAllEntries() : Int

    @Query("SELECT * FROM Trending_repo_table")
    fun getAllTrendingRepos(): List<TrendingRepoEntity>

    @Query("SELECT * FROM Trending_repo_table ORDER BY "+"STARS"+" ASC")
    fun getAllTrendingReposSortByStars(): List<TrendingRepoEntity>

    @Query("SELECT * FROM Trending_repo_table ORDER BY "+"FORKS"+" ASC")
    fun getAllTrendingReposSortByForks(): List<TrendingRepoEntity>

    @Query("SELECT COUNT(id) FROM Trending_repo_table")
    fun getCount():Int

}