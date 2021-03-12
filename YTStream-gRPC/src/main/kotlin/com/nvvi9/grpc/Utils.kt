package com.nvvi9.grpc

import com.nvvi9.ytstream.model.Thumbnail
import com.nvvi9.ytstream.model.VideoData
import com.nvvi9.ytstream.model.VideoDetails
import com.nvvi9.ytstream.model.streams.Extension
import com.nvvi9.ytstream.model.streams.Stream
import com.nvvi9.ytstream.model.streams.StreamDetails
import com.nvvi9.AudioCodec as AudioCodecGrpc
import com.nvvi9.Extension as ExtensionGrpc
import com.nvvi9.Stream as StreamGrpc
import com.nvvi9.StreamDetails as StreamDetailsGrpc
import com.nvvi9.StreamType as StreamTypeGrpc
import com.nvvi9.Thumbnail as ThumbnailGrpc
import com.nvvi9.VideoCodec as VideoCodecGrpc
import com.nvvi9.VideoData as VideoDataGrpc
import com.nvvi9.VideoDetails as VideoDetailsGrpc

fun VideoData.toVideoDataGrpc() =
    videoDetails.toVideoDetailsGrpc()?.let { details ->
        VideoDataGrpc.newBuilder().apply {
            videoDetails = details
            addAllStreams(streams.mapNotNull { it.toStreamGroc() })
        }?.build()
    }


fun VideoDetails.toVideoDetailsGrpc() =
    VideoDetailsGrpc.newBuilder()?.also {
        it.id = id
        it.title = title
        it.channel = channel
        it.channelId = channelId
        it.description = description
        durationSeconds?.let { duration ->
            it.setDurationSeconds(duration)
        }
        viewCount?.let { count ->
            it.setViewCount(count)
        }
        thumbnails.forEach { thumbnail ->
            it.addThumbnails(thumbnail.toThumbnailGrpc())
        }
        expiresInSeconds?.let { expires ->
            it.setExpiresInSeconds(expires)
        }
        isLiveStream?.let { isLiveStream ->
            it.isLiveStream = isLiveStream
        }
    }?.build()

fun Thumbnail.toThumbnailGrpc() =
    ThumbnailGrpc.newBuilder()
        .setWidth(width)
        .setHeight(height)
        .setUrl(url)
        .build()

fun Stream.toStreamGroc() =
    StreamGrpc.newBuilder()?.also {
        it.url = url
        it.streamDetails = streamDetails.toStreamDetailsGrpc()
    }?.build()

fun StreamDetails.toStreamDetailsGrpc() =
    StreamDetailsGrpc.newBuilder()?.also {
        it.itag = itag
        it.streamType = StreamTypeGrpc.valueOf(type.name)
        it.extension =
            if (extension == Extension.`3GP`) ExtensionGrpc.THREE_GP else ExtensionGrpc.valueOf(extension.name)
        audioCodec?.let { codec ->
            it.audioCodec = AudioCodecGrpc.valueOf(codec.name)
        }
        videoCodec?.let { codec ->
            it.videoCodec = VideoCodecGrpc.valueOf(codec.name)
        }
        quality?.let { quality ->
            it.quality = quality
        }
        bitrate?.let { bitrate ->
            it.bitrate = bitrate
        }
        fps?.let { fps ->
            it.fps = fps
        }
    }?.build()
