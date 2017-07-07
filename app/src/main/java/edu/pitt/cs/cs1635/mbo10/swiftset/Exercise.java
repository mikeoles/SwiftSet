package edu.pitt.cs.cs1635.mbo10.swiftset;

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
