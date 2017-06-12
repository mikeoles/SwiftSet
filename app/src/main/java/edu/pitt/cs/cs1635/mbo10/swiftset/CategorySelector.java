package edu.pitt.cs.cs1635.mbo10.swiftset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CategorySelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selector);
        SortingGroup selectedGroup = getCategory();
        ArrayList<SortingCategory> categories = selectedGroup.getCategories();

        LinearLayout l = (LinearLayout) findViewById(R.id.categoryList);
        if(((LinearLayout) l).getChildCount() > 0){
            ((LinearLayout) l).removeAllViews();
        }
        for(SortingCategory sc:categories){
            Button newButton = new Button(this);
            newButton.setText(sc.getName());
            l.addView(newButton);
        }
    }

    //Gets the sorting group that has been chosen by the user in the main activity
    private SortingGroup getCategory(){
        String sgName = "";
        Bundle extras = getIntent().getExtras();
        sgName = extras.getString("sorting_category_name");
        SortingGroup sg = MainActivity.getSGByName(sgName);
        return sg;
    }
}
