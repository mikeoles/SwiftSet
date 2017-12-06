package com.michaeloles.swiftset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

//Shows user a preview of the app
//Contains gifs with examples of each use of the app
//Has a next and skip button
//Has a brief text description of each use case
public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        int[] images = new int[]{R.drawable.ic_angle};
        LinearLayout gifView = (LinearLayout) findViewById(R.id.gifView);
        for (int i=0 ; i<20; i++){
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource (images[i]);
            gifView.addView(iv);
        }
    }
}
