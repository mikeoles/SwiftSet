package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.R;
import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;

public class Angle extends SortingGroup implements Serializable {
    public Angle(){
        this.groupIcon = R.drawable.ic_angle;

        this.setName("Level Of Incline");
        this.addOption(new SortingCategory("Incline", "Angle", "Incline"));
        this.addOption( new SortingCategory("Flat","Angle","Flat"));
        this.addOption( new SortingCategory("Decline","Angle","Decline"));
        this.addOption( new SortingCategory("Vertical","Angle","Vertical"));
    }
}