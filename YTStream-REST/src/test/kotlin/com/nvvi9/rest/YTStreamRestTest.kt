package com.nvvi9.rest

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.vertx.core.json.Json
import io.vertx.core.json.JsonArray
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class YTStreamRestTest {

    private val id = arrayOf(
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
        "UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA",
    )

    private val baseUrl = "http://localhost:8081"

    private lateinit var ktor: HttpClient

    @Before
    fun init() {
        ktor = HttpClient(OkHttp) {
            engine {
                preconfigured = OkHttpClient.Builder()
                    .addNetworkInterceptor {
                        it.proceed(
                            it.request()
                                .newBuilder()
                                .header(
                                    "User-Agent",
                                    "Mozilla/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20100101 Firefox/10.0"
                                )
                                .build()
                        )
                    }
                    .build()
            }
            expectSuccess = true
        }
    }

    @Test
    fun `video data`() = runBlocking {
        checkDataLost(((Json.decodeValue(ktor.get<String>("${baseUrl}/api/v1/videodata?id=${id.joinToString("+")}")) as JsonArray).toList() as List<*>).size)
    }

    @Test
    fun `video details`() = runBlocking {
        checkDataLost(((Json.decodeValue(ktor.get<String>("${baseUrl}/api/v1/videodetails?id=${id.joinToString("+")}")) as JsonArray).toList() as List<*>).size)
    }

    private fun checkDataLost(size: Int) {
        assertTrue("data lost: ${id.size - size}", id.size == size)
    }
}