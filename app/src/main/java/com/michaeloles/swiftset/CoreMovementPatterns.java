package com.michaeloles.swiftset;

import java.io.Serializable;

public class CoreMovementPatterns extends SortingGroup implements Serializable {
    public CoreMovementPatterns(){
        this.setName("Movement Patterns");
        this.addOption(new SortingCategory("Rotation", "Movement", "Rotation"));
        this.addOption( new SortingCategory("Hip Flexion","Movement","Hip Flexion"));
        this.addOption( new SortingCategory("Lateral Flexion","Movement","Lateral Flexion"));
        this.addOption(new SortingCategory("Anti-Rotation", "Movement", "Anti-Rotation"));
        this.addOption(new SortingCategory("Anti-Extension", "Movement", "Anti-Extension"));
        this.addOption(new SortingCategory("Anti-Lateral Flexion", "Movement", "Anti-Lateral"));
    }
}