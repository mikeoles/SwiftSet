package com.michaeloles.swiftset;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Oles on 8/29/2017.
 */
public class SavedExercises {

    private static ArrayList<String> savedExerciseList = new ArrayList<>();

    public static void setSavedExerciseList(ArrayList<String> savedExerciseList) {
        SavedExercises.savedExerciseList = savedExerciseList;
    }

    //Adds an exercise to the list
    public static int addExercise(String selectedExercise,Context context) {
        savedExerciseList.add(selectedExercise);
        SharedPreferences sharedPrefs = context.getSharedPreferences("savedExercises", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("unsaved_exercises", android.text.TextUtils.join(",", savedExerciseList));
        editor.commit();
        return savedExerciseList.size();
    }

    public static void clearSavedList(Context context) {
        savedExerciseList.clear();
        SharedPreferences sharedPrefs = context.getSharedPreferences("savedExercises", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("unsaved_exercises","");
        editor.commit();
    }

    public static void resetExerciseList(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences("savedExercises",Context.MODE_PRIVATE);
        String exerciseString = sharedPrefs.getString("unsaved_exercises", "");
        SavedExercises.savedExerciseList = new ArrayList<String>(Arrays.asList(exerciseString.split(",")));
        if(savedExerciseList.get(0).equals("")) savedExerciseList.remove(0);
    }

    //Getters and Setters
    public static ArrayList<String> getSavedExerciseList() {
        return savedExerciseList;
    }

    //This is called if a template is being passed into a workout
    //ChosenOptions contains all the sorting categories
    //The critical info from each category is stored in a string:
    //name/sortBy/dbColumnName-...(continues for each category)
    public static int addExercise(ArrayList<SortingCategory> chosenOptions, Context context){
        String s = "";
        for(SortingCategory sc:chosenOptions){
            s += sc.getName()+"&"+sc.getSortBy()+"&"+sc.getDbColumnName()+"-";
        }
        savedExerciseList.add(s);
        SharedPreferences sharedPrefs = context.getSharedPreferences("savedExercises", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("unsaved_exercises", android.text.TextUtils.join(",", savedExerciseList));
        editor.commit();
        return savedExerciseList.size();
    }
}
