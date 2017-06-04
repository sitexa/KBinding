package com.sitexa.android.network.service.caishuo

import com.sitexa.android.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by open on 04/06/2017.
 */
class InsertHeadersInterceptor(private val apiKey: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("X-Client-Key", apiKey)
        requestBuilder.addHeader("X-SN-Code", ApplicationContext.deviceId)
        requestBuilder.addHeader("X-Client-Version", ApplicationContext.version)
        return chain.proceed(requestBuilder.build())
    }
}
