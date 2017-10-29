package com.michaeloles.swiftset;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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
import java.util.Locale;
import java.util.Random;

public class WorkoutViewer extends AppCompatActivity {

    private WorkoutDBHandler dbHandler;
    private String name = "";
    ArrayAdapter<String> adapter;
    ArrayList<String> exerciseNames;
    private DatePickerDialog.OnDateSetListener mDateSetListner;
    private TextView workoutDate;
    private Calendar newDate;
    private Workout loadedWorkout;
    private static ArrayList<Boolean> isTemplate = new ArrayList<>();
    private static HashMap<Integer,ArrayList<SortingCategory>> templatesByIndex = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_viewer);
        setTitle("Workouts");
        if(getIntent().hasExtra("calendar_selection")) {
            Bundle extras = getIntent().getExtras();
            loadedWorkout = (Workout) extras.getSerializable("calendar_selection");
            if (loadedWorkout != null) {

                SavedExercises.setSavedExerciseList(loadedWorkout.getExerciseNames());
            }
            addExerciseButtons(loadedWorkout);
        }else {
            ArrayList<String> exerciseList = SavedExercises.getSavedExerciseList();
            ArrayList<String> exerciseListCopy = new ArrayList<>();
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
        input.setText(workoutName.getText());
        final boolean firstSave = workoutName.getText().length() == 0;
        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                name = input.getText().toString();
                Workout w;
                if(!firstSave){
                    w = loadedWorkout;
                    if(newDate!=null) {
                        w.setDate(newDate);
                    }
                }else{
                    w = new Workout(name,Calendar.getInstance(),SavedExercises.getSavedExerciseList());
                }
                if (w.numExercises() != 0) {
                    dbHandler = new WorkoutDBHandler(context, null, null, 1);
                    dbHandler.deleteWorkout(w.getName());
                    dbHandler.addWorkout(w);
                }
                dbHandler.numWorkouts();
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
        name.setVisibility(View.GONE);
        date.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        clear.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);

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

        int i=0;
        for(Workout w:workouts) {
            if(i>4) break;
            menu.getMenu().add(w.getName());
            i++;
        }

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String name = item.getTitle().toString();
                for (Workout w : workouts) {
                    if (w.getName().equals(name)) {
                        loadedWorkout = w;
                        SavedExercises.setSavedExerciseList(loadedWorkout.getExerciseNames());
                        addExerciseButtons(w);
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
        if(name.length()>0) {
            workoutDate.setText(date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + date.get(Calendar.DAY_OF_MONTH) + " " + date.get(Calendar.YEAR));
            workoutDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar,0,0,0);
            workoutDate.setVisibility(View.VISIBLE);
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
            mDateSetListner = new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
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
                String selectedFromList = "";
                if(!isTemplate.get(position)) {
                    //If the list item is not  a template just open the exercise
                    selectedFromList = (String) (exListView.getItemAtPosition(position));
                }else{
                    //If it is a template find a random exercise matching those search results
                    selectedFromList = WorkoutViewer.openRandomExFromTemplate(position,getApplicationContext());
                }
                if(selectedFromList==null){
                    Toast.makeText(view.getContext(),"No Matching Exercises Found",Toast.LENGTH_LONG);
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

    //Open Exercise From Template
    private static String openRandomExFromTemplate(int position,Context context) {
        ArrayList<SortingCategory> toSortBy = templatesByIndex.get(position);
        ExerciseDb db = new ExerciseDb(context);
        for(SortingCategory sc:toSortBy){
            db.removeRows(sc.getDbColumnName(),sc.getSortBy());
        }
        ArrayList<String> colList = db.getColumnsList();
        if(colList.size()==0){
            return null;
        }
        final HashMap<String,String> urls = db.getUrls();

        Random r = new Random();
        int rand = r.nextInt(colList.size());

        return colList.get(rand);
    }

    //Creates a map of the index of each element in the listview that's a template to a list of the sortingCategories in that template
    //Also creates a list of booleans so you can figure out if an element selected in an exercise or a template
    private void createMaps(ArrayList<String> en) {
        isTemplate = new ArrayList<>();
        templatesByIndex = new HashMap<>();
        for(int i=0;i<en.size();i++){
            String name = en.get(i);
            if(name.contains("&")){
                isTemplate.add(true);
                ArrayList<SortingCategory> sortingBy = new ArrayList<>();
                String[] categoriesList = name.split("-");
                String newTemplateString = "";
                for(int j=0;j<categoriesList.length;j++){
                    String[] categoryParams = categoriesList[j].split("&");
                    newTemplateString += " " + categoryParams[0] + " /";
                    SortingCategory s = new SortingCategory(categoryParams[0],categoryParams[1],categoryParams[2]);
                    sortingBy.add(s);
                }
                templatesByIndex.put(i,sortingBy);
                newTemplateString = newTemplateString.substring(0,newTemplateString.length()-1);
                newTemplateString = newTemplateString.toUpperCase();
                en.set(i,newTemplateString);
            }else{
                isTemplate.add(false);
            }
        }
    }

    //Removes an exercise from the workout (From both the screen and the savedExercises arrayList)
    private void deleteExercise(int indexClicked) {
        //remove from screen
        exerciseNames.remove(indexClicked);//where arg2 is position of item you click
        if(loadedWorkout!=null) {
            loadedWorkout.removeExercise(indexClicked);//Removes this exercise from the workout Object
        }

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
    public static void setListViewHeightBasedOnChildren(ListView listView) {
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
}