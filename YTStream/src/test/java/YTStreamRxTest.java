import com.nvvi9.YTStream;
import com.nvvi9.model.VideoData;
import com.nvvi9.model.VideoDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@RunWith(JUnit4.class)
public class YTStreamRxTest {

    private final YTStream ytStream = new YTStream();
    private final String[] id = {"UqLRqzTp6Rk", "u0BetD0OAcs", "uKM9ZuQB3MA", "1nX0kF2UwDc", "kfugSz3m_zA"};

    @Test
    public void videoDataExtraction() {
        ytStream.extractVideoDataObservable(id)
                .blockingSubscribe(this::checkVideoData);
    }

    @Test
    public void videoDetailsExtraction() {
        ytStream.extractVideoDetailsObservable(id)
                .blockingSubscribe(this::checkVideoDetails);
    }

    private void checkVideoData(VideoData videoData) {
        assertNotNull("null videoData", videoData);
        checkVideoDetails(videoData.getVideoDetails());
        assertFalse("empty streams " + videoData.getVideoDetails().getId(), videoData.getStreams().isEmpty());

    }

    private void checkVideoDetails(VideoDetails videoDetails) {
        assertNotNull("null videoDetails", videoDetails);
        assertNotNull("null id", videoDetails.getId());
        assertNotNull("null channel " + videoDetails.getId(), videoDetails.getChannel());
        assertNotNull("null title " + videoDetails.getId(), videoDetails.getTitle());
        assertNotNull("null expiresInSeconds " + videoDetails.getId(), videoDetails.getExpiresInSeconds());
    }
}