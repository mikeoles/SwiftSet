package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;

public class Stability extends SortingGroup implements Serializable {
    public Stability(){
        this.setName("Stability/Balance Exercise");
        this.addOption(new SortingCategory("Stability","Stability","1"));
    }
}