package com.gitcoffee.jek.network

import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import com.gitcoffee.jek.BuildConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ArrayRequest<T>(
    method: Int = GET,
    url: String,
    requestHeaders: HashMap<String, String> = HashMap(),
    requestPostParams: HashMap<String, String> = HashMap(),
    requestPriority: Priority = Priority.NORMAL,
    requestBody: String? = null,
    errorListener: Response.ErrorListener,
    private val responseType: Class<T>,
    private val responseListener: Response.Listener<List<T>>
) : BaseRequest<List<T>>(
    method,
    url,
    requestHeaders,
    requestPostParams,
    requestPriority,
    requestBody,
    errorListener
) {

    override fun parseNetworkResponse(response: NetworkResponse?): Response<List<T>> {
        val responseStr = String(response?.data!!)

        if (BuildConfig.SHOW_LOGS) {
            Log.d("url", url)
            Log.d("response", responseStr)
        }

        val typeToken = TypeToken.getParameterized(ArrayList::class.java, responseType).type

        return Response.success(
            Gson().fromJson(responseStr, typeToken),
            HttpHeaderParser.parseCacheHeaders(response)
        )
    }

    override fun deliverResponse(response: List<T>) {
        responseListener.onResponse(response)
    }


}