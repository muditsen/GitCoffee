package com.gitcoffee.jek.presentation.common

import com.android.volley.*
import com.gitcoffee.jek.data.Constants
import com.gitcoffee.jek.network.NetworkCustomError

object AppUtility {
    fun getNetworkError(volleyError: VolleyError): NetworkCustomError {


        val str = volleyError.networkResponse?.let { String(volleyError.networkResponse.data) } ?:let { "" }
        val statusCode = volleyError.networkResponse?.let { volleyError.networkResponse.statusCode } ?:let { 500 }

        return when (volleyError) {
            is TimeoutError -> NetworkCustomError(Constants.VOLLEY_TIME_OUT_ERROR,status = statusCode)
            is NoConnectionError -> NetworkCustomError(Constants.VOLLEY_NO_CONNECTION_ERROR,status = statusCode)
            is ServerError -> NetworkCustomError("${Constants.VOLLEY_SERVER_ERROR} $str",status = statusCode)
            is AuthFailureError -> NetworkCustomError("${Constants.VOLLEY_AUTH_ERROR} $str",status = statusCode)
            is ParseError-> NetworkCustomError(Constants.VOLLEY_PARSING_ERROR,status = statusCode)
            else -> NetworkCustomError(Constants.VOLLEY_COMMON_ERROR,status = statusCode)
        }
    }
}