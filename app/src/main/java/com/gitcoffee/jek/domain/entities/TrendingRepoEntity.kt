package com.gitcoffee.jek.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_repo_table")
data class TrendingRepoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val author: String?,
    val name: String?,
    val avatar: String?,
    val url: String?,
    val description: String?,
    val language: String?,
    val languageColor: String?,
    val stars: Int,
    val forks: Int,
    val currentPeriodStars: Int
)

data class BuiltByItem(
    val user: String?,
    val href: String?,
    val avatar: String?
)