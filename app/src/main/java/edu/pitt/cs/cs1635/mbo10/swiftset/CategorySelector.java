package edu.pitt.cs.cs1635.mbo10.swiftset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.Serializable;
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

        final ArrayList<SortingCategory> names=new ArrayList<>();

        int i =0;
        for(SortingCategory sc:categories){
            names.add(sc);
            Button newButton = new Button(this);
            newButton.setText(sc.getName());
            newButton.setId(i);
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("chosen_sorting_category", (Serializable) names.get(v.getId()));
                    startActivity(intent);
                }
            });
            l.addView(newButton);
            i++;
        }
    }

    //Gets the sorting group that has been chosen by the user in the main activity
    private SortingGroup getCategory(){
        String sgName = "";
        Bundle extras = getIntent().getExtras();
        sgName = extras.getString("sorting_group_name");
        SortingGroup sg = MainActivity.getSGByName(sgName);
        MainActivity.markSGUsedByName(sgName);
        return sg;
    }
}
