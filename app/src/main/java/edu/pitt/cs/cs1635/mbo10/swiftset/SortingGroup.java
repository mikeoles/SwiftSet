package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.util.ArrayList;

/**
 * Created by Oles on 5/29/2017.
 * A sorting group is a method in which exercises can be narrowed down.
 * Ex: Push/Pull/Legs, Muscle Group, Equipment used
 * @
 */
public class SortingGroup {

    //All of the sorting groups that cant be used after this sorting group is used
    //Ex: You can't sort by Upper/Lower after Muscle Group is called
    private ArrayList<SortingGroup> cantFollow;
    //All of the categories that can be chosen from this sorting group
    //Ex: Muscle Groups categories: Chest, Triceps, Quads, Lats...
    private ArrayList<String> categories;
    private String name;

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
    protected void addOption(String option) {
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

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<SortingGroup> getCantFollow() {
        return cantFollow;
    }

    public void setCantFollow(ArrayList<SortingGroup> cantFollow) {
        this.cantFollow = cantFollow;
    }
}
