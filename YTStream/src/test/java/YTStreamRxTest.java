import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@RunWith(JUnit4.class)
public class YTStreamRxTest {

    private final YTStream ytStream = new YTStream();
    private final String[] id = {"KK2OXwEke2Y", "K-a8s8OLBSE", "dYk-yCsuTUo", "dPhwbZBvW2o", "BjhW3vBA1QU", "5JHrqeOELvM", "PLutQQzjig4",
            "7vfnAY4fCbo", "UcLSjQx8GXA", "CblSCoHyEXY", "XATUH8O75qc", "_E7K4Tz9Uq4", "MjYnV92EThs", "3UyImq01Nww"};

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
