package edu.pitt.cs.cs1635.mbo10.swiftset;

import com.michaeloles.swiftset.ExerciseViewer;
import com.michaeloles.swiftset.YoutubeData;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

//TODO database with id 742 has no url
//TODO convert db to using only id

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 * Testing youtube urls should convert any possible youtube url to a video code and a number of milliseconds
 * This thing always seems to fail so the more tests the better
 * May want to add tests for incorrect urls if we wind up allowing the users to add their own exercises
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
    public void shortUrlHighNumberTime() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://youtu.be/2xJIw_zE46I?t=161");
        assertEquals(data.startTimeMillis,161000);
        assertEquals(data.videoCode,"2xJIw_zE46I");
    }

    @Test
    public void shortUrlWithDash() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://youtu.be/omTvjcic-Yg?t=103");
        assertEquals(data.startTimeMillis,103000);
        assertEquals(data.videoCode,"omTvjcic-Yg");
    }

    @Test public void playlistShortUrlNumberTimeQuestion() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://youtu.be/28QH0_e8fVI&list=PLBbS7swFEuIiFSnUu_bKckVugr5eZn84f&t=27");
        assertEquals(data.startTimeMillis,27000);
        assertEquals(data.videoCode,"28QH0_e8fVI");
    }

    @Test public void playlistShortUrlHighNumberTimeQuestion() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://youtu.be/uvjq4cN32eQ&list=PLBbS7swFEuIiFSnUu_bKckVugr5eZn84f&t=345");
        assertEquals(data.startTimeMillis,345000);
        assertEquals(data.videoCode,"uvjq4cN32eQ");
    }

    @Test public void playlistShortUrlNoTimeQuestion() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://youtu.be/XOTO2qWRy9U&list=PLLXmeIOBkPXRWmZipoxlZSjNhotoMy6iA");
        assertEquals(data.startTimeMillis,0);
        assertEquals(data.videoCode,"XOTO2qWRy9U");
    }

    @Test public void playlistShortUrlNumberTimeAmp() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://youtu.be/28QH0_e8fVI&list=PLBbS7swFEuIiFSnUu_bKckVugr5eZn84f&t=27");
        assertEquals(data.startTimeMillis,27000);
        assertEquals(data.videoCode,"28QH0_e8fVI");
    }

    @Test public void playlistShortUrlHighNumberTimeAmp() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://youtu.be/uvjq4cN32eQ&list=PLBbS7swFEuIiFSnUu_bKckVugr5eZn84f&t=345");
        assertEquals(data.startTimeMillis,345000);
        assertEquals(data.videoCode,"uvjq4cN32eQ");
    }

    @Test public void playlistShortUrlNoTimeAmp() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://youtu.be/XOTO2qWRy9U&list=PLLXmeIOBkPXRWmZipoxlZSjNhotoMy6iA");
        assertEquals(data.startTimeMillis,0);
        assertEquals(data.videoCode,"XOTO2qWRy9U");
    }

    @Test public void playlistLongUrlNoTimeAmp() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://www.youtube.com/watch?v=gmg1M7GK__6uE&list=PLLXmeIOBkPXRWmZipoxlZSjNhotoMy6iA");
        assertEquals(data.startTimeMillis,0);
        assertEquals(data.videoCode,"gmg1M7GK__6uE");
    }

    @Test public void playlistLongUrlNumberTimeAmp() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://www.youtube.com/watch?v=heywhatsup&list=PLLXmeIOBkPXRWmZipoxlZSjNhotoMy6iA&t=1m");
        assertEquals(data.startTimeMillis,60000);
        assertEquals(data.videoCode,"heywhatsup");
    }

    @Test public void playlistLongUrlHighTimeAmp() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://www.youtube.com/watch?v=gmg1M7GK6uE&list=PLLXmeIOBkPXRWmZipoxlZSjNhotoMy6iA?t=200");
        assertEquals(data.startTimeMillis,200000);
        assertEquals(data.videoCode,"gmg1M7GK6uE");
    }

    @Test public void playlistLongUrlHighTimeQuestion() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://www.youtube.com/watch?v=1234567&list=PLLXmeIOBkPXRWmZipoxlZSjNhotoMy6iA?t=3m10s");
        assertEquals(data.startTimeMillis,190000);
        assertEquals(data.videoCode,"1234567");
    }

    @Test public void playlistLongUrlNumberTimeQuestion() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://www.youtube.com/watch?v=-gmg1M7GK6uE-&list=PLLXmeIOBkPXRWmZipoxlZSjNhotoMy6iA&t=27s");
        assertEquals(data.startTimeMillis,27000);
        assertEquals(data.videoCode,"-gmg1M7GK6uE-");
    }

    @Test public void playlistLongUrlNoTimeQuestion() throws Exception {
        YoutubeData data = ExerciseViewer.parseYoutubeUrl("https://www.youtube.com/watch?v=abdefg&list=PLLXmeIOBkPXRWmZipoxlZSjNhotoMy6iA");
        assertEquals(data.startTimeMillis,0);
        assertEquals(data.videoCode,"abcdefg");
    }

}