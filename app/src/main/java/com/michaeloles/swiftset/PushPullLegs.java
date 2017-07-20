package com.michaeloles.swiftset;

import java.io.Serializable;
import java.util.ArrayList;

public class PushPullLegs extends SortingGroup implements Serializable{

    private static ArrayList<String> pushMuscles = new ArrayList<>();
    private static ArrayList<String> pullMuscles = new ArrayList<>();
    private static ArrayList<String> legsMuscles = new ArrayList<>();

    public PushPullLegs(){
        initStandardValues();
        this.setName("Push,Pull,Legs");
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
        pullMuscles.add("lats");
        pullMuscles.add("traps");
        pullMuscles.add("rear delts");
        pullMuscles.add("biceps");

        pushMuscles.add("chest");
        pushMuscles.add("triceps");
        pushMuscles.add("shoulders");

        legsMuscles.add("quads");
        legsMuscles.add("hamstrings");
        legsMuscles.add("calves");
        legsMuscles.add("glutes");
        legsMuscles.add("hips");
    }

    //Formates the muscles into a string which can be parsed and sorted by the sqlite database
    private String formatString(ArrayList<String> array){
        StringBuilder formatted = new StringBuilder();
        for(String s:array){
            s = s + "|";
            formatted.append(s);
        }
        formatted.deleteCharAt(formatted.length()-1);
        return formatted.toString();
    }
}
