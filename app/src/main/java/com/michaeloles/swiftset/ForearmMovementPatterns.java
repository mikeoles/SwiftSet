package com.michaeloles.swiftset;

import java.io.Serializable;

public class ForearmMovementPatterns extends SortingGroup implements Serializable {
    public ForearmMovementPatterns(){
        this.setName("Movement Patterns");
        this.addOption(new SortingCategory("Hold","Movement","Hold"));
        this.addOption( new SortingCategory("Curl","Movement","Curl"));
    }
}