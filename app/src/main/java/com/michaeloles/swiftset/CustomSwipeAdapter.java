package com.michaeloles.swiftset;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * Created by Oles on 12/9/2017.
 */

public class CustomSwipeAdapter extends PagerAdapter {

    private int[] image_resources = {R.drawable.onboarding_1,
                                    R.drawable.onboarding_2,
                                    R.drawable.onboarding_3,
                                    R.drawable.onboarding_1,
                                    R.drawable.onboarding_3};
    private String[] descriptions = {"Find the perfect exercise",
                                    "View tutorial videos and save exercises",
                                    "Save exercise types to make workout templates",
                                    "Save any workout or template",
                                    "\"Build Workout\" turns templates into unique workouts"};
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context context){
        this.context = context;
    }

    @Override
    //Return +1 so we can use the last one to go back to the Main Activity
    public int getCount() {
        return image_resources.length+1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
            ImageView imageView = (ImageView) item_view.findViewById((R.id.image_view));
            TextView textView = (TextView) item_view.findViewById(R.id.image_count);
            if(position<getCount()-1) {
                imageView.setImageResource(image_resources[position]);
                textView.setText(descriptions[position]);
            }
            container.addView(item_view);
            return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
