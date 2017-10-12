package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.R;
import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;
import java.util.ArrayList;

public class PushPullLegs extends SortingGroup implements Serializable{

    private static ArrayList<String> pushMuscles = new ArrayList<>();
    private static ArrayList<String> pullMuscles = new ArrayList<>();
    private static ArrayList<String> legsMuscles = new ArrayList<>();

    public PushPullLegs(){
        this.groupIcon = R.drawable.ic_pushpulllegs;
        initStandardValues();
        this.setName("Push,Pull,Legs");
        this.addCantFollow(Plyometrics.class);
        this.addCantFollow(MuscleGroup.class);
        SortingCategory push = new SortingCategory("Push","Primary",formatString(pushMuscles));
        this.addOption(push);
        SortingCategory pull = new SortingCategory("Pull","Primary",formatString(pullMuscles));
        this.addOption(pull);
        SortingCategory legs = new SortingCategory("Legs","Primary",formatString(legsMuscles));
        this.addOption(legs);        
    }

    //Initializes push pull legs with standard values
    //May add a feature to allow the user to chose custom values in the future
    private void initStandardValues() {
        pullMuscles.add("Lats");
        pullMuscles.add("Traps");
        pullMuscles.add("Rear Delts");
        pullMuscles.add("Biceps");

        pushMuscles.add("Chest");
        pushMuscles.add("Triceps");
        pushMuscles.add("Shoulders");

        legsMuscles.add("Quads");
        legsMuscles.add("Hamstrings");
        legsMuscles.add("Calf");
        legsMuscles.add("Glutes");
        legsMuscles.add("Hips");
    }

    //Formates the muscles into a string which can be parsed and sorted by the sqlite database
    private String formatString(ArrayList<String> array){
        StringBuilder formatted = new StringBuilder();
        for(String s:array){
            s = s + "/";
            formatted.append(s);
        }
        formatted.deleteCharAt(formatted.length()-1);
        return formatted.toString();
    }
}
