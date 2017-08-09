package com.michaeloles.swiftset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CategorySelector extends AppCompatActivity {

    ArrayList<String> selectedStrings = new ArrayList<String>();
    LinearLayout l;
    ArrayList<SortingCategory> listOfCategories;
    ArrayList<SortingCategory> userSelectedCategories = new ArrayList<SortingCategory>();
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
        listOfCategories=new ArrayList<>();//helps get the selected category onclick

        //If there is only one possible option that can be chosen, select it automatically for the user
        if(categories.size()==1){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("chosen_sorting_category",categories.get(0));
            startActivity(intent);
            return;
        }

        if(selectedGroup.isMultiChoice){
            for (int i = 0; i < categories.size(); i++) {

                //Create checkboxes for each category
                final CheckBox newCheckbox = new CheckBox(this);
                newCheckbox.setText(categories.get(i).getName());
                newCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            selectedStrings.add(newCheckbox.getText().toString());
                        }else{
                            selectedStrings.remove(newCheckbox.getText().toString());
                        }
                    }
                });
                l.addView(newCheckbox);
            }

            createSelectButton();

        }else{
            //Loops through each category from the group and creates a button for them
            for (int i = 0; i < categories.size(); i++) {
                listOfCategories.add(categories.get(i));
                Button newButton = new Button(this);
                newButton.setText(categories.get(i).getName());
                newButton.setId(i);

                //sends the selected category back to the main class when selected
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        //v.getId() contains the ID of the button the user selected
                        //using that ID get the category the user selected from listOfCategories
                        userSelectedCategories.add(listOfCategories.get(v.getId()));
                        intent.putExtra("chosen_sorting_category",userSelectedCategories);
                        startActivity(intent);
                    }
                });
                l.addView(newButton);
            }
        }
    }

    //Adds a select button to the layout to return which checkboxes have been selected
    private void createSelectButton() {
        Button button = new Button(this);
        button.setText("Select");
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //If at least one category is selected pass a string containing those categories to the mainActivity
                if(selectedStrings.size()>0) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("chosen_sorting_category", createCategoriesArray(selectedStrings));
                    startActivity(intent);
                }
            }
        });
        l.addView(button);
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
}
