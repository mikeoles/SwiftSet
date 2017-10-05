package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.R;
import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;

public class Grip extends SortingGroup implements Serializable {
    public Grip(){
        this.groupIcon = R.drawable.ic_grip;
        this.setName("Grip");
        this.addOption(new SortingCategory("Overhand","Grip","Overhand"));
        this.addOption( new SortingCategory("Neutral","Grip","Neutral"));
        this.addOption( new SortingCategory("Underhand","Grip","Underhand"));
    }
}