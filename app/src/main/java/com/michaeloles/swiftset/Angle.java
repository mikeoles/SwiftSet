package com.michaeloles.swiftset;

import java.io.Serializable;
//Todo Vertical
public class Angle extends SortingGroup implements Serializable {
    public Angle(){
        this.setName("Level Of Incline");
        this.addOption(new SortingCategory("Incline","Angle","Incline"));
        this.addOption( new SortingCategory("Flat","Angle","Flat"));
        this.addOption( new SortingCategory("Decline","Angle","Decline"));
    }
}