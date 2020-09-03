package verticles

import com.fasterxml.jackson.databind.ObjectMapper
import com.nvvi9.YTStream
import io.vertx.core.eventbus.Message
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.toChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
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

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun handleVideoDetails(msg: Message<String>) {
        val id = msg.body().split(patternSplit).toTypedArray()
        ytStream.extractVideoDetails(*id)
                .catch { it.printStackTrace() }
                .filterNotNull()
                .toList().toTypedArray().let {
                    msg.reply(mapper.writeValueAsString(it))
                }

    }
}