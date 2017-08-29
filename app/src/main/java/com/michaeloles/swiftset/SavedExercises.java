package com.michaeloles.swiftset;

import java.util.ArrayList;

/**
 * Created by Oles on 8/29/2017.
 */
public class SavedExercises {
    private static ArrayList<String> savedExerciseList = new ArrayList<>();

    //Adds an exercise to the list
    //Returns false if the exercise was already in the list
    public static int addExercise(String selectedExercise) {
        savedExerciseList.add(selectedExercise);
        return savedExerciseList.size();
    }
}
