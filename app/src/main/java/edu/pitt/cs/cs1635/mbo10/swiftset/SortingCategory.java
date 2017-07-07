package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.io.Serializable;
import java.util.ArrayList;

public class SortingCategory implements Serializable{
    public String name = "";
    //Options that are now made available because this sorting group was chosen
    //Ex: Fly is available after chest is chosen
    public ArrayList<SortingGroup> newOptions = new ArrayList<>();
    //The name of the column in the database that this category will sort by
    public String dbColumnName;

    //The value that you will select from dbColumnName when shrinking the exercise field
    public String sortBy;

    public SortingCategory(String name, ArrayList<SortingGroup> newOptions, String dbColumnName, String sortBy) {
        this.name = name;
        this.newOptions = newOptions;
        this.dbColumnName = dbColumnName;
        this.sortBy = sortBy;
    }

    public SortingCategory(String name, String dbColumnName, String sortBy) {
        this.name = name;
        this.dbColumnName = dbColumnName;
        this.sortBy = sortBy;
        this.newOptions = new ArrayList<>();
    }

    public void addNewOptions(SortingGroup newOptions){
        this.newOptions.add(newOptions);
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SortingGroup> getNewOptions() {
        return newOptions;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getDbColumnName() {
        return dbColumnName;
    }
}
