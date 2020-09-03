import com.nvvi9.YTStream
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx3.collect
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
    private val id = arrayOf("UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "KK2OXwEke2Y0", "kfugSz3m_zA")

    @Test
    fun `video data extraction`() = runBlocking {
        ytStream.extractVideoData(*id).toList().forEach {
            assertFalse("empty streams ${it?.videoDetails?.id}", it?.streams?.isEmpty() == true)
        }
    }

    @Test
    fun `video details extraction`() = runBlocking {
        ytStream.extractVideoDetails(*id).toList().forEach {
            assertNotNull("null id", it?.id)
            assertNotNull("null channel ${it?.id}", it?.channel)
            assertNotNull("null title ${it?.id}", it?.title)
            assertNotNull("null expiresInSeconds ${it?.id}", it?.expiresInSeconds)
        }
    }

    @Test
    fun `video data rx`() = runBlocking {
        ytStream.extractVideoDataObservable(*id).collect {
            assertFalse("empty streams ${it?.videoDetails?.id}", it?.streams?.isEmpty() == true)
        }
    }
}