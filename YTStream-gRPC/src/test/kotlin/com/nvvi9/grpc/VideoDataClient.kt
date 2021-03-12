package com.nvvi9.grpc

import com.nvvi9.IdQuery
import com.nvvi9.VideoDataServiceGrpcKt
import io.grpc.ManagedChannel
import java.io.Closeable
import java.util.concurrent.TimeUnit

class VideoDataClient(private val channel: ManagedChannel) : Closeable {

    private val stub = VideoDataServiceGrpcKt.VideoDataServiceCoroutineStub(channel)

    override fun close() {
        channel.shutdown().awaitTermination(10, TimeUnit.SECONDS)
    }

    fun getVideoData(vararg id: String) =
        buildIdQuery(*id)
            .let(stub::getVideoData)

    fun getVideoDetails(vararg id: String) =
        buildIdQuery(*id)
            .let(stub::getVideoDetails)

    private fun buildIdQuery(vararg id: String) =
        IdQuery.newBuilder()
            .addAllId(id.toList())
            .build()
}