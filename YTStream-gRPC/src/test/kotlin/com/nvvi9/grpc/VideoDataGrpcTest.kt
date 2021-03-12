package com.nvvi9.grpc

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class VideoDataGrpcTest {

    private lateinit var channel: ManagedChannel

    private lateinit var videoDataClient: VideoDataClient

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

    @Before
    fun `init client`() {
        channel = ManagedChannelBuilder
            .forAddress("localhost", 8080)
            .usePlaintext()
            .build()

        videoDataClient = VideoDataClient(channel)
    }

    @Test
    fun `video data`() = runBlocking {
        videoDataClient
            .getVideoData(*id)
            .toList()
            .size
            .let { checkLostData(it) }
    }

    @Test
    fun `video details`() = runBlocking {
        videoDataClient
            .getVideoDetails(*id)
            .toList()
            .size
            .let { checkLostData(it) }
    }

    private fun checkLostData(size: Int) {
        assertTrue("data lost: ${id.size - size}", id.size == size)
    }
}