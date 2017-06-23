package edu.pitt.cs.cs1635.mbo10.swiftset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<SortingGroup> currentOptions = new ArrayList<>();//all the current ways the exercises can still be sorted
    public static ArrayList<SortingGroup> usedOptions = new ArrayList<>();//all the sorting groups that have already been used
    private ExerciseDb db; //Database that holds all exercise
    //On the first time opening the app create menu options, after that update based on user selections
    private static boolean firstTimeCreated = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new ExerciseDb(this);
        if(firstTimeCreated) {
            addMainMenuOptions();
            firstTimeCreated = false;
        }else{
            Bundle extras = getIntent().getExtras();
            SortingCategory chosenSc = (SortingCategory) extras.getSerializable("chosen_sorting_category");
            ArrayList<SortingGroup> newOptions = chosenSc.getNewOptions();
            for(SortingGroup sg:newOptions){
                currentOptions.add(sg);
            }
        }
        LinearLayout l = (LinearLayout) findViewById(R.id.allOptions);
        final ArrayList<String> names=new ArrayList<>();

        for(int i=0; i<currentOptions.size(); i++){
            Button newButton = new Button(this);
            SortingGroup s = currentOptions.get(i);
            newButton.setText(s.getName());
            newButton.setId(i);
            names.add(s.getName());
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),CategorySelector.class);
                    intent.putExtra("sorting_group_name",names.get(v.getId()));
                    startActivity(intent);
                }
            });
            l.addView(newButton);
        }
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
        currentOptions.add(new MuscleGroup());
        currentOptions.add(new PushPullLegs());
    }

    public void viewExercises(View view) {
        Intent intent = new Intent(this, ExerciseSelector.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    //returns a sortingGroup based on its name
    public static SortingGroup getSGByName(String name){
        for(SortingGroup s:currentOptions){
            if(s.getName().equals(name)){
                return s;
            }
        }
        return null;
    }

    //returns a sortingGroup based on its name
    public static boolean markSGUsedByName(String name){
        for(SortingGroup s:currentOptions){
            if(s.getName().equals(name)){
                usedOptions.add(s);
                currentOptions.remove(s);
                return true;
            }
        }
        return false;
    }
}