package com.michaeloles.swiftset;

import java.io.Serializable;

public class LatMovementPatterns extends SortingGroup implements Serializable {
    public LatMovementPatterns(){
        this.setName("Movement Patterns");
        SortingCategory horizontalpull = new SortingCategory("Horizontal Pull", "Movement", "Horizontal");
        horizontalpull.addNewOptions(new Angle());
        this.addOption(horizontalpull);
        SortingCategory verticalpull = new SortingCategory("Vertical Pull","Movement","Vertical");
        verticalpull.addNewOptions(new Grip());
        this.addOption(verticalpull);
        this.addOption(new SortingCategory("Pullover Variation", "Movement", "Pullover"));
        SortingCategory pullup = new SortingCategory("Pullup Variations", "Movement", "Pullup");
        pullup.addNewOptions(new Grip());
        this.addOption(pullup);
    }
}