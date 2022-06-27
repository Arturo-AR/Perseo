package com.cv.perseo.network

import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .url("https://imgur-apiv3.p.rapidapi.com/3/image")
            .addHeader("content-type", "application/x-www-form-urlencoded")
            .addHeader("authorization", "Bearer e55203f7ebf2af09df574186d9d6982f67e4a4c8")
            .addHeader("x-rapidapi-host", "imgur-apiv3.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "e6c7d07086msh98ed42a4c6435fcp1f2f2ejsndc2408d5c224")
            .build()
        return chain.proceed(request)
    }
}