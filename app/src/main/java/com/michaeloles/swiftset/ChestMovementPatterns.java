package com.michaeloles.swiftset;

import java.io.Serializable;

public class ChestMovementPatterns extends SortingGroup implements Serializable {
    public ChestMovementPatterns(){
        this.setName("Movement Patterns");
        SortingCategory fly = new SortingCategory("Fly","Movement","Fly");
        fly.addNewOptions(new Angle());
        this.addOption(fly);
        SortingCategory horizontalpress = new SortingCategory("Horizontal Press","Movement","Horizontal Press");
        horizontalpress.addNewOptions(new Grip());
        horizontalpress.addNewOptions(new Angle());
        this.addOption(horizontalpress);
        this.addOption( new SortingCategory("Pushups","Movement","Pushups"));
    }
}