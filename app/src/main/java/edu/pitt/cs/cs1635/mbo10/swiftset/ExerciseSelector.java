package edu.pitt.cs.cs1635.mbo10.swiftset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

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
        final HashMap<String,String> urls = remaining.getUrls();
        String[] searchResults = s.toArray(new String[s.size()]);

        ListAdapter la = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResults);
        final ListView exListView = (ListView) findViewById(R.id.exerciseList);
        exListView.setAdapter(la);
        exListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedFromList = (String) (exListView.getItemAtPosition(position));
                String selectedUrl = urls.get(selectedFromList);
                Intent intent = new Intent(view.getContext(), ExerciseViewer.class);
                intent.putExtra("selected_exercise", selectedFromList);
                intent.putExtra("selected_url", selectedUrl);
                Log.v("test123", "HERE");
                startActivity(intent);
            }
        });
    }

}
