package com.nvvi9.grpc

import io.grpc.ServerBuilder

class VideoDataServer {
    private val port = System.getenv("PORT").toInt()
    private val server =
        ServerBuilder
            .forPort(port)
            .addService(VideoDataService())
            .build()

    fun start() {
        server.start()
    }

    fun blockUntilShutDown() {
        server?.awaitTermination()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val videoDataServer = VideoDataServer()
            videoDataServer.start()
            videoDataServer.blockUntilShutDown()
        }
    }
}