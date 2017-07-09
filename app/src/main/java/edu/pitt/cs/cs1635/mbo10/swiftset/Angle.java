package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.io.Serializable;

public class Angle extends SortingGroup implements Serializable {
    public Angle(){
        this.setName("Level Of Incline");
        this.addOption(new SortingCategory("Incline","Angle","Incline"));
        this.addOption( new SortingCategory("Neutral","Angle","Neutral"));
        this.addOption( new SortingCategory("Decline","Angle","Decline"));
    }
}