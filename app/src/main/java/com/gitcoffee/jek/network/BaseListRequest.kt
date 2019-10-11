package com.gitcoffee.jek.network

import com.android.volley.*
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BaseListRequest<T>(
    method: Int = GET,
    url: String,
    private val headers: HashMap<String, String> = HashMap(),
    private val postParams: HashMap<String, String> = HashMap(),
    private var requestPriority: Priority = Priority.NORMAL,
    private val responseType: Class<T>,
    private val requestBody: String? = null,
    errorListener: Response.ErrorListener,
    private val responseListener: Response.Listener<List<T>>
) : Request<List<T>>(method, url, errorListener) {


    /**
     * Charset for request.
     */
    private val protocolCharset = "utf-8"

    /**
     * Content type for request.
     */
    private val protocolContentType =
        String.format("application/json; charset=%s", protocolCharset)


    override fun parseNetworkResponse(response: NetworkResponse?): Response<List<T>> {
        val responseStr = String(response?.data!!)

        val typeToken = TypeToken.getParameterized(ArrayList::class.java, responseType).type
        return Response.success(
            Gson().fromJson(responseStr, typeToken),
            HttpHeaderParser.parseCacheHeaders(response)
        )
    }

    override fun deliverResponse(response: List<T>) {
        responseListener.onResponse(response)
    }

    override fun deliverError(error: VolleyError?) {
        super.deliverError(error)
    }


    override fun getParams(): MutableMap<String, String> {
        if (postParams.isEmpty()) {
            return super.getParams()
        }
        return postParams
    }

    override fun getHeaders(): MutableMap<String, String> {
        if (headers.isEmpty()) {
            return super.getParams()
        }


        super.getParams().putAll(headers)

        return super.getParams()
    }

    override fun getBody(): ByteArray {
        return requestBody?.toByteArray(charset(protocolCharset)) ?: super.getBody()
    }

    override fun getBodyContentType(): String {
        try {
            return if (postParams.isNotEmpty()) "application/x-www-form-urlencoded" else protocolContentType
        } catch (authFailureError: AuthFailureError) {
            authFailureError.printStackTrace()
        }

        return protocolContentType
    }

    override fun getPriority(): Request.Priority {
        return requestPriority
    }

    fun setRequestPriority(priority: Request.Priority) {
        this.requestPriority = priority
    }

    fun execute(){
        VolleySingleton.getInstance().addToRequestQueue(this)
    }
}