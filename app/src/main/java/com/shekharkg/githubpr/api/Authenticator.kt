package com.shekharkg.githubpr.api

import okhttp3.Credentials
import okhttp3.Interceptor

class Authenticator(Username: String, Password: String): Interceptor {
    private var credentials: String = Credentials.basic(Username, Password)

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credentials).build()

        return chain.proceed(request)
    }
}