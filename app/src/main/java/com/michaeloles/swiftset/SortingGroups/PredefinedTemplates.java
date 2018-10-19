package com.michaeloles.swiftset.SortingGroups;

import android.content.Context;

import com.michaeloles.swiftset.Workout;
import com.michaeloles.swiftset.WorkoutDBHandler;

import java.util.ArrayList;
import java.util.Calendar;

public class PredefinedTemplates {

    //Makes some preset templates for the user the first time the open the app
    public static void addTemplates(Context context,TemplateType type) {
        //Create a workout w for each template and call dbHandler.addWorkout(w);
        ArrayList<Workout> templatesToAdd = new ArrayList<>();
        if(type == TemplateType.STARTER){
            templatesToAdd = createStarterTemplates();
        }

        WorkoutDBHandler dbHandler = new WorkoutDBHandler(context, null, null, 1);
        for(Workout w : templatesToAdd){
            dbHandler.deleteWorkout(w.getName());
            dbHandler.addWorkout(w);
        }
    }

    //Starter templates for when the app is first opened up
    private static ArrayList<Workout> createStarterTemplates() {
        ArrayList<Workout> templates = new ArrayList<>();
        ArrayList<String> exerciseNames = new ArrayList<>();

        Workout hotel = new Workout("Hotel Workout");
        exerciseNames.add("Legs&Quads/Hamstrings/Calf/Glutes/Hips/Quads/Hamstrings/Calf/Glutes/Hips&Primary#Bodyweight&Bodyweight&Equipment#");
        exerciseNames.add("Chest&Chest&Primary#Pushups&Pushup&Movement#");
        exerciseNames.add("182");//Human Pullover
        exerciseNames.add("Bodyweight&Bodyweight&Equipment#Shoulders&Shoulders&Primary#");
        exerciseNames.add("Legs&Quads/Hamstrings/Calf/Glutes/Hips/Quads/Hamstrings/Calf/Glutes/Hips/Quads/Hamstrings/Calf/Glutes/Hips/Quads/Hamstrings/Calf/Glutes/Hips/Quads/Hamstrings/Calf/Glutes/Hips/Quads/Hamstrings/Calf/Glutes/Hips/Quads/Hamstrings/Calf/Glutes/Hips&Primary#Bodyweight&Bodyweight&Equipment#Unilateral&1&Unilateral#");
        exerciseNames.add("631");//Doorway Bicep Curls
        exerciseNames.add("Core&Core&Primary#Bodyweight&Bodyweight&Equipment#Hip Flexion&Hip Flexion&Movement#");
        exerciseNames.add("Core&Core&Primary#Bodyweight&Bodyweight&Equipment#Rotation&Rotation&Movement#");
        exerciseNames.add("Core&Core&Primary#Bodyweight&Bodyweight&Equipment#Lateral Flexion&Lateral Flexion&Movement#");
        hotel.setTemplate(true);
        hotel.setExerciseNames(exerciseNames);
        exerciseNames.clear();
        templates.add(hotel);

        Workout beginnerLower = new Workout("Beginner Lowerbody");
        exerciseNames.add("263");//Goblet Squat
        exerciseNames.add("335");//Calf Raises
        exerciseNames.add("Hamstrings&Hamstrings&Primary#Beginner&1&Difficulty#");
        exerciseNames.add("Quads&Quads&Primary#Beginner&1&Difficulty#");
        exerciseNames.add("298");//Banded Good Mornings
        exerciseNames.add("Glutes&Glutes&Primary#Unilateral&1&Unilateral#Beginner&1&Difficulty#");
        beginnerLower.setTemplate(true);
        beginnerLower.setExerciseNames(exerciseNames);
        exerciseNames.clear();
        templates.add(beginnerLower);

        Workout beginnerUpper = new Workout("Beginner Upperbody");
        exerciseNames.add("1");//Bench Press
        exerciseNames.add("671");//Band Assisted Chip Ups
        exerciseNames.add("Shoulders&Shoulders&Primary#Overhead Press&Overhead&Movement#Beginner&1&Difficulty#");
        exerciseNames.add("Rear Delts&Rear Delts&Primary#Beginner&1&Difficulty#");
        exerciseNames.add("Biceps&Biceps&Primary#Dumbbell&Dumbbell&Equipment#");
        exerciseNames.add("Triceps&Triceps&Primary#Isolation&Isolation&Joint#Beginner&1&Difficulty#");
        beginnerUpper.setTemplate(true);
        beginnerUpper.setExerciseNames(exerciseNames);
        exerciseNames.clear();
        templates.add(beginnerUpper);

        Workout core = new Workout("Core Circuit");
        exerciseNames.add("3");//Dumbbell Farmers Carries
        exerciseNames.add("Core&Core&Primary#Anti-Extension&Anti Extension&Movement#");
        exerciseNames.add("Core&Core&Primary#Anti-Rotation&Anti Rotation&Movement#");
        exerciseNames.add("Core&Core&Primary#Bodyweight&Bodyweight&Equipment#Hip Flexion&Hip Flexion&Movement#");
        exerciseNames.add("Core&Core&Primary#Bodyweight&Bodyweight&Equipment#Rotation&Rotation&Movement#");
        exerciseNames.add("Core&Core&Primary#Bodyweight&Bodyweight&Equipment#Lateral Flexion&Lateral Flexion&Movement#");
        core.setTemplate(true);
        core.setExerciseNames(exerciseNames);
        core.setDate(Calendar.getInstance());
        exerciseNames.clear();
        templates.add(core);

        return  templates;
    }

    public enum TemplateType
    {
        STARTER;
    }

}
