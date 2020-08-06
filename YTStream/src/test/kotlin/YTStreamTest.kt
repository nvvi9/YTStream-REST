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
    private val id = arrayOf(
        "KK2OXwEke2Y", "K-a8s8OLBSE", "dYk-yCsuTUo", "dPhwbZBvW2o", "BjhW3vBA1QU", "5JHrqeOELvM", "PLutQQzjig4",
        "7vfnAY4fCbo", "UcLSjQx8GXA", "CblSCoHyEXY", "XATUH8O75qc", "_E7K4Tz9Uq4", "MjYnV92EThs", "3UyImq01Nww"
    )

    @Test
    fun `video data extraction`() = runBlocking {
        ytStream.extractVideoData(*id).toList().forEach {
            assertFalse("empty streams ${it.videoDetails.id}", it.streams.isEmpty())
        }
    }

    @Test
    fun `video details extraction`() = runBlocking {
        ytStream.extractVideoDetails(*id).toList().forEach {
            assertNotNull("null id", it.id)
            assertNotNull("null channel", it.channel)
            assertNotNull("null title", it.title)
            assertNotNull("null expiresInSeconds", it.expiresInSeconds)
        }
    }

    @Test
    fun `video data rx`() = runBlocking {
        ytStream.extractVideoDataObservable(*id).collect {
            assertFalse("empty streams", it.streams.isEmpty())
        }
    }
}