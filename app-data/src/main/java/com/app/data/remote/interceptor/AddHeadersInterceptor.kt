package com.app.data.remote.interceptor

import com.app.data.local.prefers.PrefersHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddHeadersInterceptor(
    private val mPreferHelper: PrefersHelper
) : Interceptor {

    @Throws(IOException::class)
    override
    fun  intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("token", "" + mPreferHelper.getToken())
//            .header("x-api-key", "")
            .method(original.method(), original.body())

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}