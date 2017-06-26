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

        addButtons();
    }

    //Gets the sorting group that has been chosen by the user in the main activity
    private SortingGroup getCategory(){
        Bundle extras = getIntent().getExtras();
        SortingGroup chosenSG = (SortingGroup) extras.getSerializable("chosen_sorting_group");
        MainActivity.removeSortingGroup(chosenSG);
        return chosenSG;
    }

    //Adds buttons for each category to the screen
    private void addButtons(){
        SortingGroup selectedGroup = getCategory();

        removeCantFollows(selectedGroup);

        ArrayList<SortingCategory> categories = selectedGroup.getCategories();
        LinearLayout l = (LinearLayout) findViewById(R.id.categoryList);
        final ArrayList<SortingCategory> names=new ArrayList<>();//helps get the selected category onclick

        int i =0;
        for(SortingCategory sc:categories){
            names.add(sc);
            Button newButton = new Button(this);
            newButton.setText(sc.getName());
            newButton.setId(i);
            //sends the selected category back to the main class when selected
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("chosen_sorting_category",names.get(v.getId()));
                    startActivity(intent);
                }
            });
            l.addView(newButton);
            i++;
        }
    }

    //removes the sorting groups from the list that cant be used anymore based on what the user chose
    private void removeCantFollows(SortingGroup selectedGroup) {
        ArrayList<Class> cantFollowChosen = selectedGroup.getCantFollow();
        for(Class cf:cantFollowChosen){
            MainActivity.removeSortingGroup(cf);
        }
    }
}
