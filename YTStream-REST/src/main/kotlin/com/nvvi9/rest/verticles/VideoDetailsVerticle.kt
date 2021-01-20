package com.nvvi9.rest.verticles

import com.nvvi9.rest.utils.VIDEO_DETAILS_ADDRESS
import com.nvvi9.rest.utils.eventBusConsumeAsync
import com.nvvi9.rest.utils.writeAsString
import com.nvvi9.ytstream.YTStream
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.toList


@FlowPreview
@ExperimentalCoroutinesApi
class VideoDetailsVerticle : CoroutineVerticle() {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun start() {
        vertx.eventBusConsumeAsync<String>(VIDEO_DETAILS_ADDRESS) { message ->
            message.body().split(Regex("[\\s+,]+")).toTypedArray().let { id ->
                YTStream().extractVideoDetails(*id)
                        .filterNotNull()
                        .catch {
                            it.printStackTrace()
                            message.fail(500, it.message)
                        }.toList().let {
                            message.reply(it.writeAsString())
                        }
            }
        }
    }
}