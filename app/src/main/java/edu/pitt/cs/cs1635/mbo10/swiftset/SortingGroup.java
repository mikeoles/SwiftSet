package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Oles on 5/29/2017.
 * A sorting group is a method in which exercises can be narrowed down.
 * Ex: Push/Pull/Legs, Muscle Group, Equipment used
 * @
 */
public class SortingGroup {

    public String name;

    //All of the sorting groups that cant be used after this sorting group is used
    //Ex: You can't sort by Upper/Lower after Muscle Group is called
    //TODO after a category is chosen remove all of the cant follow from the mainOptions
    public ArrayList<SortingGroup> cantFollow = new ArrayList<>();

    //All of the categories that can be chosen from this sorting group
    //Ex: Muscle Groups categories: Chest, Triceps, Quads, Lats...
    public ArrayList<SortingCategory> categories = new ArrayList<>();;

    public SortingGroup(){
        this("");
    }

    public SortingGroup(String n){
        name = n;
        categories = new ArrayList<>();
        cantFollow = new ArrayList<>();
    }

    public int numCategories(){
        return categories.size();
    }

    //add a new category that this group can be divided into
    protected void addOption(SortingCategory option) {
        categories.add(option);
    }

    //add a new category that this group can be divided into
    protected void addCantFollow(SortingGroup cf) {
        cantFollow.add(cf);
    }


    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public ArrayList<SortingCategory> getCategories() {return categories;}

    public void setCategories(ArrayList<SortingCategory> categories) {this.categories = categories;}
}
