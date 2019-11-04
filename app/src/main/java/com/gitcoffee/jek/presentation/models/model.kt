package com.gitcoffee.jek.presentation.models

data class TrendingRepoItem(val author:String?,
                            val name:String?,
                            val avatar:String?,
                            val url:String?,
                            val description:String?,
                            val language:String?,
                            val languageColor: String?,
                            val stars:Int,
                            val forks:Int,
                            val currentPeriodStars:Int,
                            val builtBy: ArrayList<BuiltByItem>?)

data class BuiltByItem(val user:String?,
                       val href:String?,
                       val avatar: String?)