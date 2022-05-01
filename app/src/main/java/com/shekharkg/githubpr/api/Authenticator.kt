package com.shekharkg.githubpr.api

import okhttp3.Interceptor

class Authenticator(private val username: String, private val password: String) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader("Username", username)
        requestBuilder.addHeader("Password", password)

        return chain.proceed(requestBuilder.build())
    }
}