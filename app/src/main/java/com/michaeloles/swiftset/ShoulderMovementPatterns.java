package com.michaeloles.swiftset;

import java.io.Serializable;

public class ShoulderMovementPatterns extends SortingGroup implements Serializable {
    public ShoulderMovementPatterns(){
        this.setName("Movement Patterns");
        this.addOption(new SortingCategory("Overhead Press", "Movement", "Overhead"));
        this.addOption( new SortingCategory("Front Raise","Movement","Front"));
        this.addOption(new SortingCategory("Side Raise", "Movement", "Side"));
    }
}