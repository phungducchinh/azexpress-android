package com.app.data.remote.interceptor

import android.content.Context
import android.content.Intent
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class UnauthorizedInterceptor @Inject constructor(
    private val context: Context?,
    private val onTokenExpiredListener: OnTokenExpiredListener?
) : Interceptor {

    companion object {
        var TOKEN_EXPIRED_ACTION = "com.lib.data.remote.interceptor.TOKEN_EXPIRED_ACTION"
    }

    override
    fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        //test
        /*int code = HttpURLConnection.HTTP_UNAUTHORIZED;
        if (originalResponse.code() == code) {
            mTokenExpiredEvent.postValue(true);
        }*/

        //production code
        /*if (originalResponse.code() == HttpURLConnection.HTTP_UNAUTHORIZED
            || originalResponse.code() == HttpURLConnection.HTTP_FORBIDDEN
        ) {
            Log.d(
                "UnauthorizedInterceptor",
                String.format(
                    "intercept UnauthorizedInterceptor originalResponse.code() => %d",
                    originalResponse.code()
                )
            )
            context?.sendOrderedBroadcast(Intent(), TOKEN_EXPIRED_ACTION)
            onTokenExpiredListener?.onTokenExpired()
        }*/
        return originalResponse
    }

    interface OnTokenExpiredListener {
        fun onTokenExpired()
    }
}
