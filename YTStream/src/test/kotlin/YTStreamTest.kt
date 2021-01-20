import com.nvvi9.ytstream.YTStream
import com.nvvi9.ytstream.model.VideoData
import com.nvvi9.ytstream.model.VideoDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@FlowPreview
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class YTStreamTest {

    private val ytStream = YTStream()
    private val id = arrayOf("UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA")

    @Test
    fun `video data extraction`() = runBlocking {
        ytStream.extractVideoData(*id).collect {
            checkVideoData(it)
        }
    }

    @Test
    fun `video details extraction`() = runBlocking {
        ytStream.extractVideoDetails(*id).collect {
            checkVideoDetails(it)
        }
    }

    @Test
    fun `video data extraction rx`() {
        ytStream.extractVideoDataObservable(*id).blockingSubscribe {
            checkVideoData(it)
        }
    }

    @Test
    fun `video details extraction rx`() {
        ytStream.extractVideoDetailsObservable(*id).blockingSubscribe {
            checkVideoDetails(it)
        }
    }

    private fun checkVideoData(videoData: VideoData?) {
        videoData?.run {
            checkVideoDetails(videoDetails)
            assertFalse("empty streams ${videoDetails.id}", streams.isEmpty())
        } ?: assertNotNull("null videoData", videoData)
    }

    private fun checkVideoDetails(videoDetails: VideoDetails?) {
        videoDetails?.run {
            assertNotNull("null id", id)
            assertNotNull("null channel $id", channel)
            assertNotNull("null title $id", title)
            assertNotNull("null expiresInSeconds $id", expiresInSeconds)
        } ?: assertNotNull("null videoDetails", videoDetails)
    }
}