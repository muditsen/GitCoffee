package com.gitcoffee.jek.network.volleylivedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.VolleyError
import com.gitcoffee.jek.network.Resource
import com.gitcoffee.jek.network.VolleySingleton
import com.gitcoffee.jek.presentation.common.AppUtility

abstract class BaseLiveRequest<T>(
    method: Int = GET,
    url: String,
    private val headers: HashMap<String, String> = HashMap(),
    private val postParams: HashMap<String, String> = HashMap(),
    private var requestPriority: Priority = Priority.NORMAL,
    private val requestBody: String? = null,
    private val liveData: LiveData<Resource<T>>,
    errorListener: Response.ErrorListener = Response.ErrorListener {
        (liveData as MutableLiveData).value =
            Resource.error(AppUtility.getNetworkError(it))
    }
) : Request<T>(method, url, errorListener) {


    /**
     * Charset for request.
     */
    private val protocolCharset = "utf-8"

    /**
     * Content type for request.
     */
    private val protocolContentType =
        String.format("application/json; charset=%s", protocolCharset)


    override fun getParams(): MutableMap<String, String> {
        if (postParams.isEmpty()) {
            return super.getParams()
        }
        return postParams
    }

    override fun getHeaders(): MutableMap<String, String> {
        if (headers.isEmpty()) {
            return super.getHeaders()
        }

        super.getHeaders().putAll(headers)

        return super.getHeaders()
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

    fun execute() {
        VolleySingleton.getInstance().addToRequestQueue(this)
    }

    override fun deliverError(error: VolleyError?) {
        super.deliverError(error)
    }


}