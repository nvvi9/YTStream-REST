package com.nvvi9.model.extraction

import com.nvvi9.model.VideoDetails
import com.nvvi9.network.RetrofitService
import com.nvvi9.utils.ifNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow


internal class Raw(
        val videoPageSource: String,
        val videoDetails: VideoDetails
) {

    companion object {

        suspend fun fromIdFlow(id: String) = flow {
            emit(fromId(id))
        }

        private suspend fun fromId(id: String) = coroutineScope {
            val videoPageSource = async(Dispatchers.IO) {
                try {
                    RetrofitService.ytApiService.getVideoPage(id).string().replace("\\\"", "\"")
                } catch (t: Throwable) {
                    null
                }
            }

            val videoDetails = async(Dispatchers.IO) {
                VideoDetails.fromId(id)
            }

            ifNotNull(videoPageSource.await(), videoDetails.await()) { pageSource, details ->
                Raw(pageSource, details)
            }
        }
    }
}