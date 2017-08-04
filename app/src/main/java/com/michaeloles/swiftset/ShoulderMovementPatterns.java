package com.michaeloles.swiftset;

import java.io.Serializable;

public class ShoulderMovementPatterns extends SortingGroup implements Serializable {
    public ShoulderMovementPatterns(){
        this.setName("Movement Patterns");
        SortingCategory overheadPress = new SortingCategory("Overhead Press", "Movement", "Overhead");
        overheadPress.addNewOptions(new Grip());
        this.addOption(overheadPress);
        SortingCategory frontRaise = new SortingCategory("Front Raise","Movement","Front");
        frontRaise.addNewOptions(new Angle());
        this.addOption(frontRaise);
        this.addOption(new SortingCategory("Side Raise", "Movement", "Side"));
    }
}