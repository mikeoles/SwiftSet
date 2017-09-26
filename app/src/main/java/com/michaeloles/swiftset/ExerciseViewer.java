package com.michaeloles.swiftset;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
        import com.google.android.youtube.player.YouTubeInitializationResult;
        import com.google.android.youtube.player.YouTubePlayer;
        import com.google.android.youtube.player.YouTubePlayer.Provider;
        import com.google.android.youtube.player.YouTubePlayerView;

import java.text.NumberFormat;
import java.text.ParseException;

public class ExerciseViewer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private static String videoCode = "";
    private static String youtubeCode = "";
    private static int startTimeMillis = 0;
    private static String selectedExercise = "";
    private static boolean wasMusicPlaying = false;
    private static boolean badUrl = false;
    private static final String fullUrl = "youtube.com/watch?v=";
    private static final String shortUrl = "youtu.be/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_viewer);

        wasMusicPlaying = pauseMusic();
        startTimeMillis = 0;

        //Get Url From Calling Activity
        Bundle extras = getIntent().getExtras();
        selectedExercise = extras.getString("selected_exercise");
        ExerciseDb remaining = MainActivity.getRemainingDb();
        String selectedUrl = remaining.getUrlByExerciseName(selectedExercise);

        //seperate the youtube video code and time from the url
        if(selectedUrl.toLowerCase().contains(fullUrl)){//different depending on youtube.com and youtu.be urls
            youtubeCode = selectedUrl.substring(selectedUrl.lastIndexOf(fullUrl) + fullUrl.length());
        }else if(selectedUrl.toLowerCase().contains(shortUrl)){
            youtubeCode = selectedUrl.substring(selectedUrl.lastIndexOf(shortUrl) + shortUrl.length());
        }else{
            badUrl = true;
        }

        //Find the video code from the url
        //Ex: Url = https://www.youtube.com/watch?v=D5d_rkxPfuE&t=1m4s videoCode = D5d_rkxPfuE
        int endOfVideoCode = findFirstSeperator(youtubeCode);
        videoCode = youtubeCode.substring(0,endOfVideoCode);

        //If a specific time is designated in the video set the start time in milliseconds
        if(youtubeCode.contains("t=") && endOfVideoCode<youtubeCode.length()) {
            //removes the video code from the youtubeCode
            youtubeCode = youtubeCode.substring(endOfVideoCode + 1, youtubeCode.length());
            //Timecode is everything after t=
            String timecode = youtubeCode.substring(youtubeCode.indexOf("t=")+2,youtubeCode.length());
            //and everything before the first seperator
            timecode = timecode.substring(0,findFirstSeperator(timecode));
            //Ex: t=1m5s&index=2&list=WL&index=3 -> 1m5s

            if (!timecode.contains("m") && !timecode.contains("s")) {//timecode is just listed as an interger of seconds
                try {
                    startTimeMillis += startTimeMillis += ((Number) NumberFormat.getInstance().parse(timecode)).intValue() * 1000;
                } catch (ParseException e) {
                    badUrl = true;
                }
            }

            if (timecode.contains("m")) {//m in url d
                String[] parts = timecode.split("m");
                startTimeMillis = Integer.parseInt(parts[0]) * 60000;//Convert the url minutes time to milliseconds
                timecode = parts[1];
            }

            if (timecode.contains("s")) {
                try {
                    startTimeMillis += startTimeMillis += ((Number) NumberFormat.getInstance().parse(timecode)).intValue() * 1000;
                } catch (ParseException e) {
                    badUrl = true;
                }
            }
        }

        TextView t = (TextView) findViewById(R.id.exerciseTitle);
        t.setText(selectedExercise);
        if(selectedExercise.length()>0){
            youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
            youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    //Finds the index of the first location of a seperator character in the URL
    //Returns the end of the string if none are found
    private int findFirstSeperator(String s) {
        int endOfVideoCode = s.length();
        if (s.contains("&")) {
            endOfVideoCode = Math.min(endOfVideoCode, s.indexOf("&"));
        }
        if (s.contains("?")) {
            endOfVideoCode = Math.min(endOfVideoCode, s.indexOf("?"));
        }
        if (s.contains("\n")) {
            endOfVideoCode = Math.min(endOfVideoCode, s.indexOf("\n"));
        }
        //Code is after url and before the first seperator character (?/&)
        return endOfVideoCode;
    }

    //If music is playing in the background it pauses it so the youtube video can play
    //Returns if there was music playing so we know to resume it after leaving the view
    private boolean pauseMusic() {
        AudioManager mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        if (mAudioManager.isMusicActive()) {
            Intent i = new Intent("com.android.music.musicservicecommand");
            i.putExtra("command", "togglepause");
            ExerciseViewer.this.sendBroadcast(i);
            return true;
        }
        return false;
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            Log.v("olesy",videoCode + " " + Integer.toString(startTimeMillis));
            player.cueVideo(videoCode, startTimeMillis);
            player.play();
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

    public void saveExercise(View view){
        int numExercises = SavedExercises.addExercise(selectedExercise,this);
        Toast.makeText(this,"Saved! (" + numExercises + " exercises in current workout)",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        resumeMusic();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        resumeMusic();
    }

    //Resumes the outside music player after the video ends
    private void resumeMusic() {
        if(wasMusicPlaying){
            Intent i = new Intent("com.android.music.musicservicecommand");
            i.putExtra("command", "play");
            ExerciseViewer.this.sendBroadcast(i);
        }
    }
}