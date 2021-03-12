package com.nvvi9.grpc

import com.nvvi9.IdQuery
import com.nvvi9.VideoData
import com.nvvi9.VideoDataServiceGrpcKt
import com.nvvi9.VideoDetails
import com.nvvi9.ytstream.YTStream
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class VideoDataService : VideoDataServiceGrpcKt.VideoDataServiceCoroutineImplBase() {

    private val ytStream = YTStream();

    override fun getVideoData(request: IdQuery): Flow<VideoData> =
        ytStream.extractVideoData(*request.idList.toTypedArray())
            .mapNotNull { it?.toVideoDataGrpc() }

    override fun getVideoDetails(request: IdQuery): Flow<VideoDetails> =
        ytStream.extractVideoDetails(*request.idList.toTypedArray())
            .mapNotNull { it?.toVideoDetailsGrpc() }
}