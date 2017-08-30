package com.michaeloles.swiftset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class WorkoutViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_viewer);
        setTitle("View Workout");
        LinearLayout l = (LinearLayout) findViewById(R.id.workoutExerciseList);

        ArrayList<String> exerciseList = SavedExercises.getSavedExerciseList();
        for (final String exerciseName:exerciseList) {
            Button newButton = new Button(this);
            newButton.setText(exerciseName);

            //sends the selected exercise to the exerise viewer on click
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ExerciseViewer.class);
                    intent.putExtra("selected_exercise",exerciseName);
                    startActivity(intent);
                }
            });
            l.addView(newButton);
        }
    }
}
