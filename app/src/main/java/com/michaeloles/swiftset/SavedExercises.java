package com.michaeloles.swiftset;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Oles on 8/29/2017.
 */
public class SavedExercises {
    private static ArrayList<String> savedExerciseList = new ArrayList<>();


    //Adds an exercise to the list
    public static int addExercise(String selectedExercise,Context context) {
        savedExerciseList.add(selectedExercise);
        SharedPreferences sharedPrefs = context.getSharedPreferences("savedExercises", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("unsaved_exercises", android.text.TextUtils.join(",", savedExerciseList));
        editor.commit();
        return savedExerciseList.size();
    }

    //Removes an exercise from certain position from the list
    public static ArrayList removeExercise(int position,Context context) {
        savedExerciseList.remove(position);
        SharedPreferences sharedPrefs = context.getSharedPreferences("savedExercises", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("unsaved_exercises", android.text.TextUtils.join(",", savedExerciseList));
        editor.commit();
        return savedExerciseList;
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
}
