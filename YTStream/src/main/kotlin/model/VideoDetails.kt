package model

import com.fasterxml.jackson.annotation.JsonIgnore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import model.extraction.RawResponse


data class VideoDetails(
        val id: String?,
        val title: String?,
        val channel: String?,
        val channelId: String?,
        val description: String?,
        val durationSeconds: Long?,
        val viewCount: Long?,
        val thumbnails: List<Thumbnail>,
        val expiresInSeconds: Long?,
        val isLiveStream: Boolean?,
        @get:JsonIgnore internal val isSignatureEncoded: Boolean,
        @get:JsonIgnore internal val statusOk: Boolean,
        @get:JsonIgnore internal val rawResponse: RawResponse
) {

    override fun toString(): String =
            "VideoDetails(id=$id, title=$title, channel=$channel, channelId=$channelId," +
                    " durationSeconds=$durationSeconds"

    companion object {

        internal suspend fun fromIdFlow(id: String) = flow {
            val raw = RawResponse.fromId(id)

            val thumbnailUrl = "https://img.youtube.com/vi/$id"

            val thumbnails = listOf(
                    Thumbnail(120, 90, "$thumbnailUrl/default.jpg"),
                    Thumbnail(320, 180, "$thumbnailUrl/mqdefault.jpg"),
                    Thumbnail(480, 360, "$thumbnailUrl/hqdefault.jpg")
            )

            emit(raw.run {
                VideoDetails(
                        id, title, author, channelId, description, durationSeconds, viewCount, thumbnails,
                        expiresInSeconds, isLiveStream, isEncoded, statusOk, this
                )
            })
        }.flowOn(Dispatchers.IO)
    }
}
