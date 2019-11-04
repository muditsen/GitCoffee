package com.gitcoffee.jek.network.volleylivedata

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.NetworkResponse
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.gitcoffee.jek.BuildConfig
import com.gitcoffee.jek.network.Resource
import com.google.gson.Gson

class LiveObjectRequest<T>(
    method: Int = GET,
    url: String,
    requestHeaders: HashMap<String, String> = HashMap(),
    requestPostParams: HashMap<String, String> = HashMap(),
    requestPriority: Priority = Priority.NORMAL,
    requestBody: String? = null,
    private val liveData: MutableLiveData<Resource<T>>,
    private val responseType: Class<T>
) : BaseLiveRequest<T>(
    method,
    url,
    requestHeaders,
    requestPostParams,
    requestPriority,
    requestBody,
    liveData
) {


    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        val responseStr = String(response?.data!!)

        if (BuildConfig.SHOW_LOGS) {
            Log.d("url", url)
            Log.d("response", responseStr)
        }

        return Response.success(
            Gson().fromJson(responseStr, responseType), HttpHeaderParser.parseCacheHeaders(response)
        )
    }

    override fun deliverResponse(response: T) {
        liveData.value = Resource.success(response)
    }


}