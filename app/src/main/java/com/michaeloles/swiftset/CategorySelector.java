package com.michaeloles.swiftset;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class CategorySelector extends AppCompatActivity {

    ArrayList<String> selectedStrings = new ArrayList<>();
    LinearLayout l;
    ArrayList<SortingCategory> listOfCategories;
    ArrayList<SortingCategory> userSelectedCategories = new ArrayList<>();
    ArrayList<SortingCategory> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selector);

        addButtons();
    }

    //Gets the sorting group that has been chosen by the user in the main activity
    private SortingGroup getGroup(){
        Bundle extras = getIntent().getExtras();
        SortingGroup chosenSG = (SortingGroup) extras.getSerializable("chosen_sorting_group");
        MainActivity.removeSortingGroup(chosenSG);
        return chosenSG;
    }

    //Adds buttons for each category to the screen
    private void addButtons(){
        SortingGroup selectedGroup = getGroup();
        removeCantFollows(selectedGroup);

        //Categories from this group are used as options for the user
        categories = selectedGroup.getCategories();
        l = (LinearLayout) findViewById(R.id.categoryList);

        //If there is only one possible option that can be chosen, select it automatically for the user
        if(categories.size()==1){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("chosen_sorting_category",categories);
            startActivity(intent);
            return;
        }

        if(selectedGroup.isMultiChoice){
            addButtonsMultiChoice();
        }else{
            addButtonsSingleChoice();
        }
    }

    //Adds buttons to the screen for each sorting category when multiple cateogires may be selected
    private void addButtonsMultiChoice() {
        for (int i = 0; i < categories.size(); i++) {
            //Create checkboxes for each category
            final CheckBox newCheckbox = new CheckBox(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Set<String> defaultSet = new HashSet<>();
            HashSet<String> hiddenEquipment = new HashSet<>(sharedPreferences.getStringSet("hidden_equipment",defaultSet));
            String name = categories.get(i).getName();
            String sortBy = categories.get(i).getSortBy();
            newCheckbox.setText(name);
            newCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        selectedStrings.add(newCheckbox.getText().toString());
                    } else {
                        selectedStrings.remove(newCheckbox.getText().toString());
                    }
                }
            });
            if(!hiddenEquipment.contains(sortBy)){
                l.addView(newCheckbox);
            }

        }
        createSelectButton();
    }

    //Adds buttons to the screen for each sorting category when only one category may be selected
    private void addButtonsSingleChoice(){
        listOfCategories = new ArrayList<>();//helps get the selected category onclick
        int blueValue = 250;

        //Loops through each category from the group and creates a button for them
        for (int i = 0; i < categories.size(); i++) {
            listOfCategories.add(categories.get(i));
            Button newButton = new Button(this);
            newButton.setText(categories.get(i).getName());
            newButton.setId(i);

            GradientDrawable gd = new GradientDrawable();
            gd.setColor(Color.WHITE);
            if(blueValue>5) blueValue -= 5;
            gd.setStroke(2,Color.rgb(200, 200,blueValue));
            newButton.setBackground(gd);

            //sends the selected category back to the main class when selected
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    //v.getId() contains the ID of the button the user selected
                    //using that ID get the category the user selected from listOfCategories
                    userSelectedCategories.add(listOfCategories.get(v.getId()));
                    intent.putExtra("chosen_sorting_category", userSelectedCategories);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                }
            });
            l.addView(newButton);
        }
    }

    //Adds a select button to the layout to return which checkboxes have been selected
    private void createSelectButton() {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.catRelativeLayout);
        Button button = new Button(this);
        button.setText(R.string.select_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If at least one category is selected pass a string containing those categories to the mainActivity
                if (selectedStrings.size() > 0) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("chosen_sorting_category", createCategoriesArray(selectedStrings));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                }
            }
        });

        button.setBackgroundColor(Color.WHITE);
        button.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(button, lp);
    }

    //Creates an arraylist of sorting categories to return based on a list of strings that the user has selected
    private ArrayList<SortingCategory> createCategoriesArray(ArrayList<String> selectedStrings) {
        ArrayList<SortingCategory> ret = new ArrayList<>();
        for(String s:selectedStrings){
            for(SortingCategory sc: categories){
                if(sc.getName().equals(s)){
                    ret.add(sc);
                    break;
                }
            }
        }
        return ret;
    }

    //removes the sorting groups from the list that cant be used anymore based on what the user chose
    private void removeCantFollows(SortingGroup selectedGroup) {
        ArrayList<Class> cantFollowChosen = selectedGroup.getCantFollow();
        for(Class cf:cantFollowChosen){
            MainActivity.removeSortingGroup(cf);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
