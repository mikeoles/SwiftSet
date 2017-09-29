package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;

public class ForearmMovementPatterns extends SortingGroup implements Serializable {
    public ForearmMovementPatterns(){
        this.setName("Movement Patterns");
        this.addOption(new SortingCategory("Hold","Movement","Hold"));
        this.addOption( new SortingCategory("Curl","Movement","Curl"));
    }
}