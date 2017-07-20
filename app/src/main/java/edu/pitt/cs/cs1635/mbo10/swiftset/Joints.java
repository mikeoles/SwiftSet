package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.io.Serializable;

public class Joints extends SortingGroup implements Serializable {
    public Joints(){
        this.setName("Compound/Isolation");
        this.addOption(new SortingCategory("Compound","Joint","Compound"));
        this.addOption( new SortingCategory("Isolation","Joint","Isolation"));
    }
}