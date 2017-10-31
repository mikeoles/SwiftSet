package com.michaeloles.swiftset;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;

public class WorkoutCalendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_calendar);
        MaterialCalendarView calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        calendarView.addDecorators(new CurrentDayDecorator(this));
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                WorkoutDBHandler dbHandler = new WorkoutDBHandler(getApplicationContext(), null, null, 1);
                final ArrayList<Workout> workouts = dbHandler.getWorkouts();

                ArrayList<Workout> workoutsMatchingDate = new ArrayList<>();

                for(Workout w:workouts) {
                    CalendarDay workoutDate = CalendarDay.from(w.getDate());
                    if(workoutDate.equals(date)&&!w.isTemplate()){
                        workoutsMatchingDate.add(w);
                    }
                }

                if(workoutsMatchingDate.size()==1){
                    Intent intent = new Intent(getApplicationContext(),WorkoutViewer.class);
                    intent.putExtra("calendar_selection",workoutsMatchingDate.get(0));//Sends the chosen sorting group to the CategorySelector class when clicked
                    startActivity(intent);
                }
                if(workoutsMatchingDate.size()>1){
                    addExerciseButtons(workoutsMatchingDate);
                }
            }
        });
    }

    public void addExerciseButtons(ArrayList<Workout> w){
        LinearLayout l = (LinearLayout) findViewById(R.id.workoutsOnDate);
        l.removeAllViews();
        final ArrayList<Workout> names = new ArrayList<>();//Helps the onClick function find what group was selected

        //Add new buttons for each of the sorting groups available to the user
        for(int i=0; i<w.size(); i++){
            Button newButton = new Button(this);
            newButton.setText(w.get(i).getName());
            newButton.setId(i);
            newButton.setPadding(5,0,0,0);
            newButton.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

            final TypedValue value = new TypedValue();
            getApplicationContext().getTheme().resolveAttribute(R.attr.colorAccent, value, true);
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(Color.WHITE); // Changes this drawbale to use a single color instead of a gradient
            gd.setStroke(1, value.data);
            newButton.setCompoundDrawablePadding(5);
            newButton.setBackground(gd);
            newButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

            names.add(w.get(i));
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),WorkoutViewer.class);
                    intent.putExtra("calendar_selection",names.get(v.getId()));//Sends the chosen sorting group to the CategorySelector class when clicked
                    startActivity(intent);
                }
            });

            l.addView(newButton);
        }
    }

}

class CurrentDayDecorator implements DayViewDecorator {
    private Activity context;

    public CurrentDayDecorator(Activity context) {
        this.context = context;
    }

    //Finds which days of the calendar should have a dot on them
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        WorkoutDBHandler dbHandler = new WorkoutDBHandler(context, null, null, 1);
        HashSet<CalendarDay> usedDates = dbHandler.getDates();
        return usedDates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(8, Color.BLUE));
    }
}