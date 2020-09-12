package com.nvvi9.network

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import okhttp3.OkHttpClient

object KtorService {

    private val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor {
                it.proceed(
                        it.request()
                                .newBuilder()
                                .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20100101 Firefox/10.0")
                                .build()
                )
            }
            .build()

    val ktor = HttpClient(OkHttp) {
        engine {
            preconfigured = okHttpClient
        }

        expectSuccess = true
    }
}