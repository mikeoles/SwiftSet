package com.michaeloles.swiftset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Oles on 8/30/2017.
 */
public class Workout {
    private String name;
    private Date date;
    private ArrayList<String> exerciseNames = new ArrayList<>();

    public Workout(String name,ArrayList<String> exerciseNames){
        this(name,Calendar.getInstance().getTime(),exerciseNames);
    }

    public Workout(String name,Date date,ArrayList<String> exerciseNames){
        this.name = name;
        this.exerciseNames = exerciseNames;
        this.date = date;
    }

    public Workout(String name){
        this.name = name;
    }

    public int numExercises(){
        return exerciseNames.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String exerciseNamesToString() {
        return android.text.TextUtils.join(",", exerciseNames);
    }

    public ArrayList<String> getExerciseNames() {
        return exerciseNames;
    }

    public void setExerciseNames(ArrayList<String> exerciseNames) {
        this.exerciseNames = exerciseNames;
    }

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}
}
