package edu.pitt.cs.cs1635.mbo10.swiftset;

/**
 * Created by Oles on 6/4/2017.
 */
public class Exercise {
    private int id;
    private String name;

    public Exercise(){
    }

    public Exercise(String exerciseName){
        this.name = exerciseName;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
