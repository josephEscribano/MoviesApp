package com.example.moviesappjosephescribano.data.sources.remote.di

import com.example.moviesappjosephescribano.utils.Constantes
import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter(Constantes.API_KEY, Constantes.KEY)
            .addQueryParameter(Constantes.LANGUAGE, Constantes.LANGUAGE_VALUE)
            .build()
        val request = chain.request().newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}