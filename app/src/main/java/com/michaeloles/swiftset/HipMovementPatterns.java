package com.michaeloles.swiftset;

import java.io.Serializable;

public class HipMovementPatterns extends SortingGroup implements Serializable {
    public HipMovementPatterns(){
        this.setName("Movement Patterns");
        this.addOption(new SortingCategory("Flexion", "Movement", "Flexion"));
        this.addOption( new SortingCategory("Extension","Movement","Extension"));
        this.addOption( new SortingCategory("Adductor","Movement","Adductor"));
        this.addOption(new SortingCategory("Abductor", "Movement", "Abductor"));
    }
}