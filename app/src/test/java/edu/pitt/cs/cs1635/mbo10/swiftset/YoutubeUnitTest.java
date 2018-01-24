package edu.pitt.cs.cs1635.mbo10.swiftset;

import com.michaeloles.swiftset.ExerciseViewer;
import com.michaeloles.swiftset.YoutubeData;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class YoutubeUnitTest {
    @Test
    public void regularUrl() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://www.youtube.com/watch?v=UZi_zwL3Oq0");
        assertEquals(data.startTimeMillis,0);
        assertEquals(data.videoCode,"UZi_zwL3Oq0");
    }

    @Test
    public void regularUrlWithMinutes() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://www.youtube.com/watch?v=UZi_zwL3Oq0?t=3m");
        assertEquals(data.startTimeMillis,180000);
        assertEquals(data.videoCode,"UZi_zwL3Oq0");
    }

    @Test
    public void regularUrlWithMinutesAndSeconds() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://www.youtube.com/watch?v=rT7DgCr-3pg?t=2m9s");
        assertEquals(data.startTimeMillis,129000);
        assertEquals(data.videoCode,"rT7DgCr-3pg");
    }

    @Test
    public void regularUrlWithSeconds() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://www.youtube.com/watch?v=vthMCtgVtFw0?t=18s");
        assertEquals(data.startTimeMillis,18000);
        assertEquals(data.videoCode,"vthMCtgVtFw0");
    }

    @Test
    public void regularUrlWithHighSeconds() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://www.youtube.com/watch?v=0ckfCxEV-bs?t=70s");
        assertEquals(data.startTimeMillis,70000);
        assertEquals(data.videoCode,"0ckfCxEV-bs");
    }

    @Test
    public void shortUrl() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://youtu.be/v7hlCxtdNhc");
        assertEquals(data.startTimeMillis,0);
        assertEquals(data.videoCode,"v7hlCxtdNhc");
    }

    @Test
    public void shortUrlMinutesSeconds() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://youtu.be/fjTw-rMFk9A?t=1m40s");
        assertEquals(data.startTimeMillis,100000);
        assertEquals(data.videoCode,"fjTw-rMFk9A");
    }

    @Test
    public void shortUrlZeroMinutesSeconds() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://youtu.be/2xJIw_zE46I?t=0m30s");
        assertEquals(data.startTimeMillis,30000);
        assertEquals(data.videoCode,"2xJIw_zE46I");
    }

    @Test
    public void shortUrlNumberTime() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://youtu.be/2xJIw_zE46I?t=161");
        assertEquals(data.startTimeMillis,161000);
        assertEquals(data.videoCode,"2xJIw_zE46I");
    }
}