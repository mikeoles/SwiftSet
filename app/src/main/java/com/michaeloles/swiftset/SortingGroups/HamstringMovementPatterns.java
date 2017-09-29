package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;

public class HamstringMovementPatterns extends SortingGroup implements Serializable {
    public HamstringMovementPatterns(){
        this.setName("Movement Patterns");
        this.addOption(new SortingCategory("Leg Press Variations", "Movement", "Leg Press"));
        this.addOption( new SortingCategory("Lunge Variations","Movement","Lunge"));
        this.addOption(new SortingCategory("Hip Hinge/DeadLift Variations", "Movement", "Hinge"));
        this.addOption(new SortingCategory("Curl Variations", "Movement", "Curl"));
    }
}