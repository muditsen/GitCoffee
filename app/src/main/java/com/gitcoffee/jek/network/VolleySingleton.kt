package com.gitcoffee.jek.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack


class VolleySingleton private constructor(context: Context) {

    private val requestQueue: RequestQueue by lazy {
        val cache = DiskBasedCache(context.cacheDir, 10 * 1024 * 1024)
        val network = BasicNetwork(HurlStack())
        RequestQueue(cache,network)
    }.also {
        it.value.start()
    }

    companion object{

        private var instance: VolleySingleton? = null

        fun init(context: Context) {
            instance = VolleySingleton(context)
        }

        fun getInstance():VolleySingleton{
            checkNotNull(instance) {
                IllegalStateException("Please init volley queue with application context.")
            }
            return instance as VolleySingleton
        }
    }

    fun getReqQueue():RequestQueue{
        return requestQueue
    }

    fun<T> addToRequestQueue(request: Request<T>){
        requestQueue.add(request)
    }


}