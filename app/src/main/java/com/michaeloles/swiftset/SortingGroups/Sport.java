package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;

public class Sport extends SortingGroup implements Serializable {
    public Sport(){
        this.setName("Sport");
        this.addOption(new SortingCategory("Powerlifting", "Sport", "Powerlifting"));
        this.addOption( new SortingCategory("Strongman","Sport","Strongman"));
        this.addOption( new SortingCategory("Olympic Lifting","Sport","Olympic"));
    }
}