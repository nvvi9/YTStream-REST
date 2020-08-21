package model.extraction

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import model.VideoDetails
import network.RetrofitService


internal class Raw(
        val videoPageSource: String,
        val videoDetails: VideoDetails
) {

    companion object {

        suspend fun fromIdFlow(id: String) = flow {
            emit(fromId(id))
        }

        private suspend fun fromId(id: String): Raw {
            return coroutineScope {
                val videoPageSource = async(Dispatchers.IO) {
                    try {
                        RetrofitService.ytApiService.getVideoPage(id).string().replace("\\\"", "\"")
                    } catch (t: Throwable) {
                        null
                    }
                }

                val videoDetails = async(Dispatchers.IO) {
                    VideoDetails.fromIdFlow(id).single()
                }
                Raw(videoPageSource.await() ?: throw IllegalArgumentException("null video page"), videoDetails.await())
            }
        }
    }
}