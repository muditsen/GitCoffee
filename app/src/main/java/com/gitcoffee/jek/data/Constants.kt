package com.gitcoffee.jek.data

import com.gitcoffee.jek.BuildConfig

object Constants {

    const val SHARED_PREF_NAME = "com.gitcoffee.jek.prefs"

    const val IS_CACHE_ENABLE = "cacheEnabled"

    const val IS_CACHE_EXIST = "cacheExists"

    const val TRENDING_DATA_UPDATE_TIME = "updateTime"

    private val BASE_URL = BuildConfig.BASE_URL


    fun getGitTrendingRepoUrl(langague: String? = null, since: String? = null): String {
        return "$BASE_URL/repositories"
    }

    const val VOLLEY_TIME_OUT_ERROR = "TimeOut error: Trying increasing timeout of your request."

    const val VOLLEY_NO_CONNECTION_ERROR = "No connection error: Please check your internet connection or give Internet permission in your Manifest file."

    const val VOLLEY_SERVER_ERROR = "Server error:"

    const val VOLLEY_PARSING_ERROR = "TimeOut error: Trying increasing timeout of your request."

    const val VOLLEY_AUTH_ERROR = "Authentication Failed with reason "

    const val VOLLEY_COMMON_ERROR = "Something went wrong.!!"

}