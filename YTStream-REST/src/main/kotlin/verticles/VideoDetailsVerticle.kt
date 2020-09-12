package verticles

import com.fasterxml.jackson.databind.ObjectMapper
import com.nvvi9.YTStream
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.toList
import utils.VIDEO_DETAILS_ADDRESS
import utils.eventBusConsumeAsync


@FlowPreview
@ExperimentalCoroutinesApi
class VideoDetailsVerticle(private val mapper: ObjectMapper) : CoroutineVerticle() {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun start() {
        vertx.eventBusConsumeAsync<String>(VIDEO_DETAILS_ADDRESS) { message ->
            message.body().split(Regex("[\\s+,]+")).toTypedArray().let { id ->
                YTStream().extractVideoDetails(*id)
                        .catch {
                            it.printStackTrace()
                            message.fail(500, it.message)
                        }.filterNotNull()
                        .toList().let {
                            message.reply(mapper.writeValueAsString(it))
                        }
            }

        }
    }
}