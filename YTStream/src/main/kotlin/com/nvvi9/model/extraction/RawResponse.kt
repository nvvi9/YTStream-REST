package com.nvvi9.model.extraction

import com.nvvi9.network.RetrofitService
import com.nvvi9.utils.decode
import com.nvvi9.utils.encode
import java.util.regex.Pattern


@Suppress("BlockingMethodInNonBlockingContext")
inline class RawResponse(val raw: String) {

    val id get() = patternVideoId.matcher(raw).takeIf { it.find() }?.group(1)
    val title get() = patternTitle.matcher(raw).takeIf { it.find() }?.group(1)
    val isLiveStream get() = patternHlsvp.matcher(raw).find()
    val author get() = patternAuthor.matcher(raw).takeIf { it.find() }?.group(1)
    val channelId get() = patternChannelId.matcher(raw).takeIf { it.find() }?.group(1)
    val description get() = patternShortDescription.matcher(raw).takeIf { it.find() }?.group(1)
    val durationSeconds get() = patternLengthSeconds.matcher(raw).takeIf { it.find() }?.group(1)?.toLong()
    val viewCount get() = patternViewCount.matcher(raw).takeIf { it.find() }?.group(1)?.toLong()
    val expiresInSeconds get() = patternExpiresInSeconds.matcher(raw).takeIf { it.find() }?.group(1)?.toLong()
    val isEncoded get() = patternCipher.matcher(raw).find()
    val statusOk get() = patternStatusOk.matcher(raw).find()

    companion object {
        suspend fun fromId(id: String): RawResponse = try {
            RetrofitService.ytApiService.getVideoInfo("https://www.youtube.com/get_video_info?video_id=$id&eurl=${"https://youtube.googleapis.com/v/$id".encode()}")
        } catch (t: Throwable) {
            null
        }?.let {
            RawResponse(it.string().decode().replace("\\u0026", "&"))
        } ?: throw IllegalArgumentException("null video info")

        private val patternTitle: Pattern = Pattern.compile("\"title\"\\s*:\\s*\"(.*?)\"")
        private val patternVideoId: Pattern = Pattern.compile("\"videoId\"\\s*:\\s*\"(.+?)\"")
        private val patternAuthor: Pattern = Pattern.compile("\"author\"\\s*:\\s*\"(.+?)\"")
        private val patternChannelId: Pattern = Pattern.compile("\"channelId\"\\s*:\\s*\"(.+?)\"")
        private val patternLengthSeconds: Pattern = Pattern.compile("\"lengthSeconds\"\\s*:\\s*\"(\\d+?)\"")
        private val patternViewCount: Pattern = Pattern.compile("\"viewCount\"\\s*:\\s*\"(\\d+?)\"")
        private val patternExpiresInSeconds: Pattern = Pattern.compile("\"expiresInSeconds\"\\s*:\\s*\"(\\d+?)\"")
        private val patternShortDescription: Pattern = Pattern.compile("\"shortDescription\"\\s*:\\s*\"(.+?)\"")
        private val patternStatusOk: Pattern = Pattern.compile("status=ok(&|,|\\z)")
        private val patternHlsvp: Pattern = Pattern.compile("hlsvp=(.+?)(&|\\z)")
        private val patternCipher: Pattern = Pattern.compile("\"signatureCipher\"\\s*:\\s*\"(.+?)\"")
    }
}