package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;

public class Unilateral extends SortingGroup implements Serializable {
    public Unilateral(){
        this.setName("Unilateral/Bilateral");
        this.addOption(new SortingCategory("Unilateral","Unilateral","1"));
        this.addOption( new SortingCategory("Bilateral","Unilateral","0"));
    }
}