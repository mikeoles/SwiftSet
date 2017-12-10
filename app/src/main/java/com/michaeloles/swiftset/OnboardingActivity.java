package com.michaeloles.swiftset;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

//Shows user a preview of the app
//Contains gifs with examples of each use of the app
//Has a next and skip button
//Has a brief text description of each use case
public class OnboardingActivity extends AppCompatActivity {
    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//Remove title bar
        setContentView(R.layout.activity_onboarding);
        //View pager style set ing activity_onboarding.xml
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position)
            {
                if(position==viewPager.getAdapter().getCount()-1){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("reset_main",true);
                    startActivity(intent);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
    }
}
