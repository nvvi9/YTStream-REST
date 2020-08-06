package verticles

import YTStream
import com.fasterxml.jackson.databind.ObjectMapper
import io.vertx.core.eventbus.Message
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.toChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import utils.VIDEO_DETAILS_ADDRESS
import java.util.regex.Pattern


@FlowPreview
@ExperimentalCoroutinesApi
class VideoDetailsVerticle(private val ytStream: YTStream, private val mapper: ObjectMapper) : CoroutineVerticle() {

    private val patternSplit = Pattern.compile("[\\s+,]+")

    override suspend fun start() {
        vertx.eventBus().consumer<String>(VIDEO_DETAILS_ADDRESS).toChannel(vertx).consumeEach {
            launch(Dispatchers.IO) { handleVideoDetails(it) }
        }
    }

    private suspend fun handleVideoDetails(msg: Message<String>) {
        try {
            val id = msg.body().split(patternSplit).toTypedArray()
            val videoDetails = ytStream.extractVideoDetails(*id).toList().toTypedArray()
            msg.reply(mapper.writeValueAsString(videoDetails))
        } catch (e: Exception) {
            msg.fail(0, e.message)
            e.printStackTrace()
        }
    }
}