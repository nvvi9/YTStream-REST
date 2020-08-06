package model

import js.JsExecutor
import kotlinx.coroutines.flow.flow
import model.extraction.EncodedStreams
import model.streams.Stream


data class VideoData(
    val videoDetails: VideoDetails,
    val streams: List<Stream>
) {

    companion object {

        internal suspend fun fromEncodedStreamsFlow(encodedStreams: EncodedStreams) = flow<VideoData> {
            val details = encodedStreams.videoDetails
            val streams = encodedStreams.streams.toMutableList()


            emit((encodedStreams.jsCode?.let { script ->
                JsExecutor.executeScript("decode", script).split("\n").let {
                    VideoData(details, streams.encodeStreams(it, encodedStreams.encodedSignatures))
                }
            } ?: VideoData(details, streams)))
        }

        private fun MutableList<Stream>.encodeStreams(
            decodeSignatures: List<String>,
            encSignatures: Map<Int, String>
        ): List<Stream> = apply {
            encSignatures.keys.zip(decodeSignatures).forEach { (key, signature) ->
                find { it.streamDetails.itag == key }.also { remove(it) }?.url?.plus("&sig=$signature")
                    ?.let { Stream.fromItag(key, it) }?.let { add(it) }
            }
        }
    }
}