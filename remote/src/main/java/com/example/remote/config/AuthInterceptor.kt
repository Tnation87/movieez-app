package com.example.remote.config

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(
            "Authorization",
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjNWViMDA1NjhmYWY0YzVkM2I3ODhiMDhhNmZjYmU1MSIsInN1YiI6IjY2MDk3NWMwMjgzZWQ5MDE0OTE4OWJkYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3xOc_wzFL5_k0bG7qbGiDkdvTVpHmOaEMpifB2o3yd0"
        )

        return chain.proceed(requestBuilder.build())
    }
}