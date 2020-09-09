import com.nvvi9.YTStream;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@RunWith(JUnit4.class)
public class YTStreamRxTest {

    private final YTStream ytStream = new YTStream();
    private final String[] id = {"UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "KK2OXwEke2Y0", "kfugSz3m_zA"};

    @Test
    public void videoDataExtraction() {
        ytStream.extractVideoDataObservable(id).blockingSubscribe(videoData -> assertFalse(videoData.getStreams().isEmpty()));
    }

    @Test
    public void videoDetailsExtraction() {
        ytStream.extractVideoDetailsObservable(id)
                .observeOn(Schedulers.io())
                .blockingSubscribe(videoDetails -> {
                    assertNotNull(videoDetails.getId());
                    assertNotNull(videoDetails.getChannel());
                    assertNotNull(videoDetails.getTitle());
                    assertNotNull(videoDetails.getExpiresInSeconds());
                    assertFalse(videoDetails.getThumbnails().isEmpty());
                });
    }
}