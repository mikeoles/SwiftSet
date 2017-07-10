package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.io.Serializable;

public class Grip extends SortingGroup implements Serializable {
    public Grip(){
        this.setName("Grip");
        this.addOption(new SortingCategory("Overhand","Grip","Overhand"));
        this.addOption( new SortingCategory("Neutral","Grip","Neutral"));
        this.addOption( new SortingCategory("Underhand","Grip","Underhand"));
    }
}