package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;

public class NeckMovementPatterns extends SortingGroup implements Serializable {
    public NeckMovementPatterns(){
        this.setName("Movement Patterns");
        this.addOption(new SortingCategory("Rotation", "Movement", "Rotation"));
        this.addOption( new SortingCategory("Lateral Flexion","Movement","Lateral Flexion"));
        this.addOption(new SortingCategory("Extension", "Movement", "Extension"));
        this.addOption(new SortingCategory("Flexion", "Movement", "Flexion"));
    }
}
