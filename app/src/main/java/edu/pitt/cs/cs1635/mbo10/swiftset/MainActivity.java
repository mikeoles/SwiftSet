package edu.pitt.cs.cs1635.mbo10.swiftset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<SortingGroup> currentOptions = new ArrayList<>();//all the current ways the exercises can still be sorted
    public static ArrayList<SortingGroup> removedOptions = new ArrayList<>();//all the sorting groups that have already been used or cant be used
    private static ExerciseDb remainingDb; // Updated to hold the remaining exercises
    //On the first time opening the app create menu options, after that update based on user selections
    private static boolean firstTimeCreated = true;
    private static boolean firstTimeSelected = true;
    private static String sortingPathString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(firstTimeCreated) {
            remainingDb = new ExerciseDb(this);
            addMainMenuOptions();
            remainingDb.resetDatabase();
            firstTimeCreated = false;
        }else{
            //The sorting category chosen by the user in CategorySelector.java.  Will be used to shrink the exercise pool
            Bundle extras = getIntent().getExtras();
            SortingCategory chosenSc = (SortingCategory) extras.getSerializable("chosen_sorting_category");

            String scName = chosenSc.getName();
            updateSortingPath(scName);

            //New groups that can be added because of the chose sorting category (Ex: Fly can be added after Chest is chosen)
            ArrayList<SortingGroup> newOptions = chosenSc.getNewOptions();
            for(SortingGroup sg:newOptions){
                if(!removedOptions.contains(sg)) {
                    currentOptions.add(sg);
                }
            }

            String dbSortCategory = chosenSc.getDbColumnName();
            String dbSortBy = chosenSc.getSortBy();
            dbSearch(remainingDb, dbSortBy, dbSortCategory);

            firstTimeSelected = false;
        }
        setViewAllText();
        addButtons();
    }

    //Sets the text for the view all button with the number of exercises remaining in the pool displayed
    private void setViewAllText() {
        Button viewAll = (Button) findViewById(R.id.viewAll);
        String allExercises = "View All Exercises (" + remainingDb.numRows() + ")";
        viewAll.setText(allExercises);
    }

    //Updates the displayed sorting path with the new category exercises are being sorted by
    private String updateSortingPath(String scName) {
        TextView sortingPath = (TextView) findViewById(R.id.sortingPath);
        if(firstTimeSelected) {
            sortingPathString = scName;
        }else{
            sortingPathString = sortingPathString + ">" + scName;
        }
        sortingPath.setText(sortingPathString);
        return sortingPathString;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
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
    public void addButtons(){
        LinearLayout l = (LinearLayout) findViewById(R.id.allOptions);
        final ArrayList<SortingGroup> names=new ArrayList<>();//Helps the onClick function find what group was selected

        //Add new buttons for each of the sorting groups available to the user
        for(int i=0; i<currentOptions.size(); i++){
            Button newButton = new Button(this);
            SortingGroup s = currentOptions.get(i);
            newButton.setText(s.getName());
            newButton.setId(i);
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