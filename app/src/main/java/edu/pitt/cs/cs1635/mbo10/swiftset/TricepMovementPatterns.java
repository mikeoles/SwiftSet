package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.io.Serializable;

public class TricepMovementPatterns extends SortingGroup implements Serializable {
    public TricepMovementPatterns(){
        this.setName("Movement Patterns");
        this.addOption(new SortingCategory("Press", "Movement", "Press"));
        this.addOption( new SortingCategory("Extension","Movement","Extension"));
        this.addOption(new SortingCategory("Dip", "Movement", "Dip"));
    }
}