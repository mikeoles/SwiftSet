package com.michaeloles.swiftset;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<SortingGroup> currentOptions = new ArrayList<>();//all the current ways the exercises can still be sorted
    public static ArrayList<SortingGroup> removedOptions = new ArrayList<>();//all the sorting groups that have already been used or cant be used
    public static ArrayList<String> chosenOptions = new ArrayList<>();//all the sorting groups that have been selected by the user
    private static ExerciseDb remainingDb; // Updated to hold the remaining exercises
    //On the first time opening the app create menu options, after that update based on user selections
    private static boolean firstTimeCreated = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(firstTimeCreated) {
            removedOptions.clear();
            currentOptions.clear();
            chosenOptions.clear();
            remainingDb = new ExerciseDb(this);
            addMainMenuOptions();
            remainingDb.resetDatabase();
            firstTimeCreated = false;
        }else{
            //The sorting category chosen by the user in CategorySelector.java.  Will be used to shrink the exercise pool
            Bundle extras = getIntent().getExtras();
            ArrayList<SortingCategory> chosenScList = (ArrayList<SortingCategory>) extras.getSerializable("chosen_sorting_category");

            String dbSortCategory = "";
            String dbSortBy = "";
            for(int i=0;i<chosenScList.size();i++) {
                SortingCategory chosenSc = chosenScList.get(i);
                chosenOptions.add(chosenSc.getName());

                //New groups that can be added because of the chose sorting category (Ex: Fly Movement Pattern can be added after Chest is chosen)
                ArrayList<SortingGroup> newOptions = chosenSc.getNewOptions();
                for (SortingGroup sg : newOptions) {
                    if (!removedOptions.contains(sg) && !currentOptions.contains(sg)) {
                        currentOptions.add(sg);
                    }
                }

                if(i==0) {
                   dbSortCategory = chosenSc.getDbColumnName();
                }
                dbSortBy += chosenSc.getSortBy() + "/";
            }
            dbSearch(remainingDb, dbSortBy, dbSortCategory);
        }
        setViewAllText();
        updateSortingPath(this);
        addButtons(this);
    }

    @Override
    public void onBackPressed() {
        firstTimeCreated = true;
        //Refresh Activity with as first time created to reset the database
        finish();
        startActivity(getIntent());
    }

    //Resets all of the progress from the user in selecting an exercise and returns to the main activity
    public void reset(View view){
        firstTimeCreated = true;
        //Refresh Activity with as first time created to reset the database
        finish();
        startActivity(getIntent());
    }

    //Sets the text for the view all button with the number of exercises remaining in the pool displayed
    private void setViewAllText() {
        Button viewAll = (Button) findViewById(R.id.viewAll);
        String allExercises = "View All Exercises (" + remainingDb.numRows() + ")";
        viewAll.setText(allExercises);
    }

    //Updates the displayed sorting path with the new category exercises are being sorted by
    private void updateSortingPath(Context context) {

        //Add new buttons for each of the sorting groups available to the user
        LinearLayout sortingPath = (LinearLayout) findViewById(R.id.sortingPath);

        for(int i=0; i<chosenOptions.size(); i++){
            Button b = new Button(this);
            String name = chosenOptions.get(i);
            b.setTextSize(9);
            b.setHeight(10);
            b.setText(name);
            sortingPath.addView(b);
        }
    }

    //Sorts through the remaining exercises and eliminates the ones that no longer fit
    private void dbSearch(ExerciseDb db, String dbSortBy, String dbSortCategory) {
        db.removeRows(dbSortBy,dbSortCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    //Creates the different sorting group classes that exercises can be sorted by
    public void addMainMenuOptions(){
        currentOptions.add(new Equipment());
        currentOptions.add(new MuscleGroup());
        currentOptions.add(new PushPullLegs());
        currentOptions.add(new Tempo());
        currentOptions.add(new Joints());
        currentOptions.add(new Unilateral());
        currentOptions.add(new Stability());
        currentOptions.add(new Sport());
    }

    public void viewExercises(View view) {
        Intent intent = new Intent(this, ExerciseSelector.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remainingDb.close();
    }


    //returns a sortingGroup based on its name
    public static boolean removeSortingGroup(SortingGroup sg){
        for(SortingGroup s:currentOptions){
            if(s.getClass().equals(sg.getClass())){
                removedOptions.add(s);
                currentOptions.remove(s);
                return true;
            }
        }
        return false;
    }

    //returns a sortingGroup based on its name
    public static boolean removeSortingGroup(Class sg){
        for(SortingGroup s:currentOptions){
            if(s.getClass().equals(sg)){
                removedOptions.add(s);
                currentOptions.remove(s);
                return true;
            }
        }
        return false;
    }

    //Adds buttons for every sorting group to the main page
    public void addButtons(Context context){
        LinearLayout l = (LinearLayout) findViewById(R.id.allOptions);

        final ArrayList<SortingGroup> names=new ArrayList<>();//Helps the onClick function find what group was selected

        //Add new buttons for each of the sorting groups available to the user
        for(int i=0; i<currentOptions.size(); i++){
            Button newButton = new Button(this);
            SortingGroup s = currentOptions.get(i);
            newButton.setText(s.getName());
            newButton.setId(i);
            newButton.setPadding(0,0,0,0);
            final TypedValue value = new TypedValue();
            context.getTheme ().resolveAttribute(R.attr.colorAccent, value, true);
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(Color.WHITE); // Changes this drawbale to use a single color instead of a gradient
            gd.setStroke(1, value.data);
            newButton.setBackground(gd);

            names.add(s);
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),CategorySelector.class);
                    intent.putExtra("chosen_sorting_group",names.get(v.getId()));//Sends the chosen sorting group to the CategorySelector class when clicked
                    startActivity(intent);
                }
            });
            l.addView(newButton);
        }
    }

    //Getters and Setters
    public static ExerciseDb getRemainingDb() {
        return remainingDb;
    }
}