package com.michaeloles.swiftset;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class ExerciseViewer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private static String youtubeCode = "";
    private static int startTimeMillis = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_viewer);

        Bundle extras = getIntent().getExtras();
        String selectedExercise = extras.getString("selected_exercise");
        String selectedUrl = extras.getString("selected_url");
        assert selectedUrl != null;
        youtubeCode = selectedUrl.substring(selectedUrl.lastIndexOf("=") + 1).toLowerCase();
        if(youtubeCode.contains("&")){
            //TODO get start time
            String[] parts = youtubeCode.split("&");
            youtubeCode = parts[0];//The code in the url the speifies the current video
            String timecode = parts[1];//The part of the url that specifices the start time
            if(timecode.contains("m")){//if both minutes and seconds are noted in url
                parts = timecode.split("m");
                startTimeMillis = Integer.parseInt(parts[0]) * 60000;//Convert the url minutes time to milliseconds
                timecode = parts[1];
            }

            if(timecode.contains("s")){
                timecode = timecode.substring(0, timecode.length() - 1);//remove trailing s from string to get number of seconds
            }

            startTimeMillis += Integer.parseInt(timecode) * 1000;
        }
        TextView t = (TextView) findViewById(R.id.exerciseTitle);
        t.setText(selectedExercise);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(youtubeCode,startTimeMillis);
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}
