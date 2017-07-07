package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.io.Serializable;

public class ChestMovementPatterns extends SortingGroup implements Serializable {
    public ChestMovementPatterns(){
        this.setName("Movement Patterns");
        this.addOption(new SortingCategory("Fly", "Movement", "Fly"));
        this.addOption( new SortingCategory("Horizontal Press","Movement","Horizontal Press"));
        this.addOption( new SortingCategory("Pushups","Movement","Pushups"));
    }
}