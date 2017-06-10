package edu.pitt.cs.cs1635.mbo10.swiftset;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<SortingGroup> mainOptions = new ArrayList<SortingGroup>();
    private ExerciseDb db;
    private String sortGroupName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new ExerciseDb(this);

        createSortingClasses();
        LinearLayout l = (LinearLayout) findViewById(R.id.allOptions);
        for(int i=0; i<mainOptions.size(); i++){
            Button newButton = new Button(this);
            SortingGroup s = mainOptions.get(i);
            newButton.setText(s.getName());
            sortGroupName = s.getName();
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),CategorySelector.class);
                    intent.putExtra("sorting_category_name",sortGroupName);
                    startActivity(intent);
                }
            });
            l.addView(newButton);
        }
        Button newButton = new Button(this);
        newButton.setText(db.dbString());
        l.addView(newButton);
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
    public void createSortingClasses(){
        mainOptions.add(new PushPullLegs());
        mainOptions.add(new MuscleGroup());
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
}