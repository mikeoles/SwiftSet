package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.io.Serializable;

public class LatMovementPatterns extends SortingGroup implements Serializable {
    public LatMovementPatterns(){
        this.setName("Movement Patterns");
        SortingCategory horizontalpull = new SortingCategory("Horizontal Pull", "Movement", "Horizontal");
        horizontalpull.addNewOptions(new Angle());
        this.addOption(horizontalpull);
        this.addOption( new SortingCategory("Vertical Pull","Movement","Vertical"));
        this.addOption(new SortingCategory("Pullover Variation", "Movement", "Pullover"));
        this.addOption(new SortingCategory("Pullup Variations", "Movement", "Pullup"));
    }
}