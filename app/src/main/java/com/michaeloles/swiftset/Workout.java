package com.michaeloles.swiftset;

import java.io.StringBufferInputStream;
import java.util.ArrayList;

/**
 * Created by Oles on 8/30/2017.
 */
public class Workout {
    private String name;
    private ArrayList<String> exerciseNames = new ArrayList<>();

    public Workout(String name,ArrayList<String> exerciseNames){
        this.name = name;
        this.exerciseNames = exerciseNames;
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
}
