package com.michaeloles.swiftset;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class WorkoutViewer extends AppCompatActivity {
    private WorkoutDBHandler dbHandler;
    private String name = "";
    ArrayAdapter<String> adapter;
    ArrayList<String> exerciseNames;//These names are shown on the screen by the array adapter listView
    private DatePickerDialog.OnDateSetListener mDateSetListner;
    private TextView workoutDate;
    private Button makeWorkout;
    private Calendar newDate;
    private Workout loadedWorkout;
    private static ArrayList<Boolean> isTemplate = new ArrayList<>();
    private static HashMap<Integer,ArrayList<SortingCategory>> templatesByIndex = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_viewer);
        this.create();
    }

    @Override
    public void onResume(){
        super.onResume();
        this.create();
    }

    public void create(){
        setTitle("Workouts");
        //If the user has selected a saved workout
        if(getIntent().hasExtra("calendar_selection")) {
            Bundle extras = getIntent().getExtras();
            loadedWorkout = (Workout) extras.getSerializable("calendar_selection");
            if (loadedWorkout != null) {
                ArrayList<String> exerciseListCopy = new ArrayList<>();
                for(String s:loadedWorkout.getExerciseNames()){
                    exerciseListCopy.add(s);

                }
                SavedExercises.setSavedExerciseList(exerciseListCopy);
            }
            addExerciseButtons(loadedWorkout);
        }else {//Loads a new unnamed workout from the saved exercises
            ArrayList<String> exerciseList = SavedExercises.getSavedExerciseList();
            ArrayList<String> exerciseListCopy = new ArrayList<>();
            //Make a copy so the SavedExercises class is not affected by changes
            for(String s:exerciseList){
                exerciseListCopy.add(s);
            }
            //tempWorkout represents a "workout" of the exersises the user has chosen but not yet saved to a workout
            Workout tempWorkout = new Workout("", exerciseListCopy);
            addExerciseButtons(tempWorkout);
        }
    }

    //Called when the user saves a workout
    //Gets a name for the workout and then saves it to the sqlite database
    public void saveWorkout(View view) {
        final Context context = view.getContext();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Saving Workout");
        alert.setMessage("Name Your Workout");

        final EditText input = new EditText(this);
        alert.setView(input);
        TextView workoutName = (TextView) findViewById(R.id.workoutName);

        //If there is no workout name, the workout has never been saved before
        final boolean firstSave = workoutName.getText().length() == 0;

        //Set a suggested name to save the workout as depending on if it already has one
        if(firstSave){
            String hint = Calendar.getInstance().get(Calendar.MONTH)+1
                    + "/" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +
                    " Workout";
            input.setText(hint);
        }else{
            input.setText(workoutName.getText());
        }
        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                name = input.getText().toString();
                Workout w;
                if(!firstSave){
                        //Save the loaded workout with updated date if necessary
                        if(newDate!=null) {
                            w = new Workout(name,newDate,SavedExercises.getSavedExerciseList());
                        }else{
                            w = new Workout(name,loadedWorkout.getDate(),SavedExercises.getSavedExerciseList());
                        }
                }else{
                    //Create a new workout to save
                    w = new Workout(name,Calendar.getInstance(),SavedExercises.getSavedExerciseList());
                }
                w.setTemplate(isTemplate.contains(true));//If there's any template exercises in the workout its a template workout

                if(name.length()<1){
                    Toast.makeText(getApplicationContext(),"Please Enter A Name",Toast.LENGTH_SHORT).show();
                }else if(w.numExercises() != 0) {
                    dbHandler = new WorkoutDBHandler(context, null, null, 1);
                    dbHandler.deleteWorkout(w.getName());
                    dbHandler.addWorkout(w);
                }

            }
        });

        alert.show();
    }

    //Delete a workout from the database
    public void deleteWorkout(View view) {
        TextView t = (TextView) findViewById(R.id.workoutName);
        String nameToDelete = t.getText().toString();
        dbHandler = new WorkoutDBHandler(view.getContext(), null, null, 1);
        dbHandler.deleteWorkout(nameToDelete);
        clearWorkout(view);
    }

    //Removes all exercise from the workout and clears the exercies from the activity
    public void clearWorkout(View view){
        SavedExercises.clearSavedList(this);
        Button save = (Button) findViewById(R.id.saveWorkoutButton);
        Button clear = (Button) findViewById(R.id.clearButton);
        Button delete = (Button) findViewById(R.id.deleteButton);
        TextView name = (TextView) findViewById(R.id.workoutName);
        TextView date = (TextView) findViewById(R.id.workoutDate);
        Button makeWorkout = (Button) findViewById(R.id.makeWorkout);
        name.setVisibility(View.GONE);
        date.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        clear.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        makeWorkout.setVisibility(View.GONE);

        //remove items from the workout list
        final ListView exListView = (ListView) findViewById(R.id.workoutExerciseList);
        exListView.setAdapter(null);
    }

    public void viewCalendar(final View view){
        Intent intent = new Intent(this,WorkoutCalendar.class);
        this.startActivity(intent);
    }

    //Allows the user to view list of workouts they've already saved
    public void viewSavedWorkouts(final View view){
        PopupMenu menu = new PopupMenu(this, view);
        dbHandler = new WorkoutDBHandler(view.getContext(), null, null, 1);
        final ArrayList<Workout> workouts = dbHandler.getWorkouts();

        Collections.sort(workouts, new Comparator<Workout>() {
            public int compare(Workout o1, Workout o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        //Shows the 5 most recent workouts that are not templates
        int i=0;
        for(Workout w:workouts) {
            if(i>4) break;
            if(!w.isTemplate()) {
                menu.getMenu().add(w.getName());
                i++;
            }
        }

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String name = item.getTitle().toString();
                for (Workout w : workouts) {
                    if (w.getName().equals(name) && !w.isTemplate()) {
                        Intent intent = new Intent(getApplicationContext(),WorkoutViewer.class);
                        intent.putExtra("calendar_selection",w);//Sends the chosen sorting group to the CategorySelector class when clicked
                        startActivity(intent);
                    }
                }
                return true;
            }
        });

        menu.show();
    }

    //Allows the user to view list of workouts they've already saved
    public void viewSavedTemplates(final View view){
        PopupMenu menu = new PopupMenu(this, view);
        dbHandler = new WorkoutDBHandler(view.getContext(), null, null, 1);
        final ArrayList<Workout> workouts = dbHandler.getWorkouts();

        Collections.sort(workouts, new Comparator<Workout>() {
            public int compare(Workout o1, Workout o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        for(Workout w:workouts) {
            if(w.isTemplate()) {
                menu.getMenu().add(w.getName());
            }
        }

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String name = item.getTitle().toString();
                for (Workout w : workouts) {
                    if (w.getName().equals(name) && w.isTemplate()) {
                        Intent intent = new Intent(getApplicationContext(),WorkoutViewer.class);
                        intent.putExtra("calendar_selection",w);//Sends the chosen sorting group to the CategorySelector class when clicked
                        startActivity(intent);
                    }
                }
                return true;
            }
        });

        menu.show();
    }

    //Adds the exercise buttons to the screen
    public void addExerciseButtons(Workout w){
        String name = w.getName();
        Calendar date = w.getDate();
        ArrayList<String> exerciseList = w.getExerciseNames();
        showEditButtons(true);
        //remove items from the workout list
        ListView l = (ListView) findViewById(R.id.workoutExerciseList);
        l.setAdapter(null);

        exerciseList.remove("");
        TextView workoutName = (TextView) findViewById(R.id.workoutName);
        workoutDate = (TextView) findViewById(R.id.workoutDate);
        makeWorkout = (Button) findViewById(R.id.makeWorkout);
        makeWorkout.setVisibility(View.GONE);
        if(name.length()>0) {
            //Date is always set either as what the user saved it or its set to the current day if not
            workoutDate.setText(date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + date.get(Calendar.DAY_OF_MONTH) + " " + date.get(Calendar.YEAR));
            workoutDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar,0,0,0);
            //Dates are only needed for workouts and you can only make a worout from a template
            if(!w.isTemplate()) {
                workoutDate.setVisibility(View.VISIBLE);
            }else{
                makeWorkout.setVisibility(View.VISIBLE);
                workoutDate.setVisibility(View.GONE);
            }
            //If the date is clicked it opens a date picker dialog to allow the user to change it
            workoutDate.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialog = new DatePickerDialog(
                            WorkoutViewer.this,
                            android.R.style.Theme_DeviceDefault_Light_Dialog,
                            mDateSetListner,
                            year,month,day);
                    dialog.show();
                }
            });
            //If the user chooses a new date from the picker, save that date to the workout
            mDateSetListner = new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    //New date is used to display the date and is also saved if the user saves the workout
                    newDate = Calendar.getInstance();
                    newDate.set(year,month,dayOfMonth);
                    workoutDate.setText(newDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + newDate.get(Calendar.DAY_OF_MONTH) + " " + newDate.get(Calendar.YEAR));
                }
            };
            workoutName.setText(name);
            workoutName.setVisibility(View.VISIBLE);
        }else{
            //Only able to delete a workout if its already been saved
            workoutName.setVisibility(View.GONE);
            workoutDate.setVisibility(View.GONE);
            Button delete = (Button) findViewById(R.id.deleteButton);
            delete.setVisibility(View.GONE);
        }

        //Dont show the save and clear buttons if there are no exercises to save or clear
        if(exerciseList.size()==0){
            showEditButtons(false);
            dbHandler = new WorkoutDBHandler(this, null, null, 1);
            if(dbHandler.numWorkouts()==0){
                Toast.makeText(this,"Looks like you don't have any saved workouts yet, search for exercises to create workouts",Toast.LENGTH_LONG).show();
            }
        }

        initList(exerciseList);
        setListViewHeightBasedOnChildren(l);
    }

    @Override
    public void onBackPressed() {
        //Avoids going back to the calendar when a user presses back, it seems better to just go to the main actvity
        if(getIntent().hasExtra("calendar_selection")) {
            Intent intent = new Intent(WorkoutViewer.this,MainActivity.class);
            intent.putExtra("reset_main",true);
            startActivity(intent);
        }else{
            super.onBackPressed();
        }
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    //Either hides or shows the edit buttons to the user
    //No edit buttons are shown if there's no exercises to save or clear from a workout
    public void showEditButtons(boolean b) {
        Button save = (Button) findViewById(R.id.saveWorkoutButton);
        Button clear = (Button) findViewById(R.id.clearButton);
        Button delete = (Button) findViewById(R.id.deleteButton);
        if(b){
            save.setVisibility(View.VISIBLE);
            clear.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }else{
            save.setVisibility(View.GONE);
            clear.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }
    }

    //Creates the array adapter based on a list of strings which represent the exercises
    private void initList(ArrayList<String> en) {
        exerciseNames = en;
        //Go Through the arraylist of strings and find which are templates and which are exercises
        createMaps(exerciseNames);

        //Creates a list with each exercise and stores the exercise name and url in the intent
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exerciseNames);
        final ListView exListView = (ListView) findViewById(R.id.workoutExerciseList);

        exListView.setAdapter(adapter);
        exListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedFromList;
                if(!isTemplate.get(position)) {
                    //If the list item is not  a template just open the exercise
                    selectedFromList = (String) (exListView.getItemAtPosition(position));
                }else{
                    //If it is a template find a random exercise matching those search results
                    selectedFromList = openRandomExFromTemplate(position,getApplicationContext());
                }
                if(selectedFromList==null){
                    Toast.makeText(view.getContext(),"No Matching Exercises Found",Toast.LENGTH_LONG).show();

                }else {
                    //Open the exercise in exerciseViewer
                    Intent intent = new Intent(view.getContext(), ExerciseViewer.class);
                    intent.putExtra("selected_exercise", selectedFromList);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            }
        });
        //Allows user to delete an item on long click
        exListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int indexClicked, long arg3) {
                final Context context = view.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Delete Exercise?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        deleteExercise(indexClicked);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });
    }

    //Open Exercise From Template given the position in the exercise list it's in
    private String openRandomExFromTemplate(int position,Context context) {
        ArrayList<SortingCategory> toSortBy = templatesByIndex.get(position);
        //Sort by each sorting category in to sort by
        ExerciseDb db = new ExerciseDb(context);
        db.resetDatabase();
        this.personalize(db,getDifficulty(),getHiddenEquipment());
        for(SortingCategory sc:toSortBy){
            db.removeRows(sc.getDbColumnName(),sc.getSortBy());
        }

        ArrayList<String> colList = db.getColumnsList();
        if(colList.size()==0){//If there are no columns available return 0
            return null;
        }

        Random r = new Random();
        int rand = r.nextInt(colList.size());
        return colList.get(rand);
    }

    //Creates a map of the index of each element in the listview that's a template to a list of the sortingCategories in that template
    //Also creates a list of booleans so you can figure out if an element selected in an exercise or a template
    private void createMaps(ArrayList<String> en) {
        isTemplate = new ArrayList<>();//Keeps track of which exercises in the workout are templates
        templatesByIndex = new HashMap<>();
        for(int i=0;i<en.size();i++){
            String name = en.get(i);
            if(name.contains("&")){//& denotes that something is a template
                isTemplate.add(true);
                ArrayList<SortingCategory> sortingBy = new ArrayList<>();//Contains each sorting category in the specific template
                String[] categoriesList = name.split("#");
                String newTemplateString = "";//String with all the sorting categories in this template
                for(int j=0;j<categoriesList.length;j++){
                    String[] categoryParams = categoriesList[j].split("&");
                    newTemplateString += " " + categoryParams[0] + " /";
                    SortingCategory s = new SortingCategory(categoryParams[0],categoryParams[1],categoryParams[2]);
                    sortingBy.add(s);
                }
                //The sorting categories for a template can be found by what index that is in the exercise list
                templatesByIndex.put(i,sortingBy);
                newTemplateString = newTemplateString.substring(0,newTemplateString.length()-1);
                newTemplateString = newTemplateString.toUpperCase();//Uppercase so user knows it's not an exercise
                en.set(i,newTemplateString);
            }else{
                isTemplate.add(false);
            }
        }
    }

    //Removes an exercise from the workout (From both the screen and the savedExercises arrayList)
    private void deleteExercise(int indexClicked) {
        //Remove from screen
        exerciseNames.remove(indexClicked);//where arg2 is position of item you click

        //Remove from the current list of exercises
        SavedExercises.removeExercise(this,indexClicked);

        //Remove from the map to sorting categories if necessary
        if(isTemplate.get(indexClicked)){
            templatesByIndex.remove(indexClicked);
        }
        //Remove from the global isTemplate list
        isTemplate.remove(indexClicked);

        adapter.notifyDataSetChanged();
        showEditButtons(!exerciseNames.isEmpty());
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    private static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    //returns if a user has allowed advanced exercises in the settings menu
    private String getDifficulty() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getString("base_difficulty_level", "3");
    }

    //Returns an arraylist of the equipment the user has selected to hide in the settings menu
    private ArrayList<String> getHiddenEquipment() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Set<String> defaultSet = new HashSet<>();
        return new ArrayList<>(sharedPreferences.getStringSet("hidden_equipment",defaultSet));
    }

    /** Is called to personalize the available results based on the users preferences
     ** @param difficultyLevel the difficulty level selected by the user 1-4, remove exercises more difficult than this
     ** @param hiddenEquipment removes equipment the user has selected in settings to they don't want
     **/
    private void personalize(ExerciseDb db, String difficultyLevel,ArrayList<String> hiddenEquipment) {
        //Removes exercises with difficulties of 4 if the user doesn't want them
        db.removeDifficultyAbove(difficultyLevel);
        db.EquipRemoveRows(hiddenEquipment);
    }

    //Generates a random workout based on the template currently selected
    public void makeWorkout(View view){
        //Get exercises from savedWorkouts
        ArrayList<String> savedExercises = SavedExercises.getSavedExerciseList();

        //Go through savedExercises and whenever there is a template, remplace it with a random exercise matching that template
        for(int i=0; i<savedExercises.size(); i++){
            if(isTemplate.get(i)) {
                String randomExercise = openRandomExFromTemplate(i, getApplicationContext());
                savedExercises.set(i,randomExercise);
            }
        }

        SavedExercises.setSavedExerciseList(savedExercises);

        //Recall onCreate with templates removed
        Intent intent = new Intent(this,WorkoutViewer.class);
        this.startActivity(intent);
    }
}