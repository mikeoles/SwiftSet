package com.michaeloles.swiftset;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WorkoutViewer extends AppCompatActivity {

    private WorkoutDBHandler dbHandler;
    private String name = "";
    ArrayAdapter<String> adapter;
    ArrayList<String> exerciseNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_viewer);
        setTitle("Workouts");
        ArrayList<String> exerciseList = SavedExercises.getSavedExerciseList();

        addExerciseButtons(exerciseList, "");
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

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                name = input.getText().toString();
                Workout w = new Workout(name, SavedExercises.getSavedExerciseList());
                if (w.numExercises() != 0) {
                    dbHandler = new WorkoutDBHandler(context, null, null, 1);
                    dbHandler.deleteWorkout(w.getName());
                    dbHandler.addWorkout(w);
                }
                Toast.makeText(context, "Workout " + name + " saved", Toast.LENGTH_SHORT).show();
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
        name.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        clear.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);

        //remove items from the workout list
        final ListView exListView = (ListView) findViewById(R.id.workoutExerciseList);
        exListView.setAdapter(null);
    }

    //Allows the user to view list of workouts they've already saved
    public void viewSavedWorkouts(final View view){
        PopupMenu menu = new PopupMenu(this, view);
        dbHandler = new WorkoutDBHandler(view.getContext(), null, null, 1);
        final ArrayList<Workout> workouts = dbHandler.getWorkouts();

        for(Workout w:workouts) {
            menu.getMenu().add(w.getName());
        }

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String name = item.getTitle().toString();
                for (Workout w : workouts) {
                    if (w.getName().equals(name)) {
                        addExerciseButtons(w.getExerciseNames(), w.getName());
                    }
                }
                return true;
            }
        });

        menu.show();
    }

    public void addExerciseButtons(ArrayList<String> exerciseList,String name){
        showEditButtons(true);
        //remove items from the workout list
        ListView l = (ListView) findViewById(R.id.workoutExerciseList);
        l.setAdapter(null);

        exerciseList.remove("");

        TextView workoutName = (TextView) findViewById(R.id.workoutName);
        if(name.length()>0) {
            workoutName.setText(name);
            workoutName.setVisibility(View.VISIBLE);
        }else{
            //Only able to delete a workout if its already been saved
            workoutName.setVisibility(View.GONE);
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
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    //Either hides or shows the edit buttons to the user
    //No edit buttons are shownif there's no exercises to save or clear from a workout
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

    private void initList(ArrayList<String> en) {
        exerciseNames = en;
        Log.v("olesy",Integer.toString(exerciseNames.size()));
        //Creates a list with each exercise and stores the exercise name and url in the intent
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exerciseNames);
        final ListView exListView = (ListView) findViewById(R.id.workoutExerciseList);
        exListView.setAdapter(adapter);
        exListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedFromList = (String) (exListView.getItemAtPosition(position));
                Intent intent = new Intent(view.getContext(), ExerciseViewer.class);
                intent.putExtra("selected_exercise", selectedFromList);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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

    //Removes an exercise from the workout (From both the screen and the savedExercises arrayList)
    private void deleteExercise(int indexClicked) {
        //remove from screen
        exerciseNames.remove(indexClicked);//where arg2 is position of item you click
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
