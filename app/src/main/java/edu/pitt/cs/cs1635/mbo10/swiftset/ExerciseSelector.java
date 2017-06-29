package edu.pitt.cs.cs1635.mbo10.swiftset;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ExerciseSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_selector);
        setTitle("Choose An Exercise");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ExerciseDb remaining = MainActivity.getRemainingDb();
        ArrayList<String> s = remaining.getColumnsList();
        String[] searchResults = s.toArray(new String[s.size()]);;
        ListAdapter la = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,searchResults);
        ListView exListView = (ListView) findViewById(R.id.exerciseList);
        exListView.setAdapter(la);
    }

}
