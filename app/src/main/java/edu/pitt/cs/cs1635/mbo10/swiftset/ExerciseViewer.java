package edu.pitt.cs.cs1635.mbo10.swiftset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ExerciseViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_viewer);

        Bundle extras = getIntent().getExtras();
        String selectedExercise = extras.getString("selected_exercise");
        String selectedUrl = extras.getString("selected_url");

        TextView t = (TextView) findViewById(R.id.exerciseTitle);
        t.setText(selectedExercise + "\n" + selectedUrl);
    }
}
