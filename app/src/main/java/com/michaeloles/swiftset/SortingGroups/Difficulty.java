package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.R;
import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;

public class Difficulty extends SortingGroup implements Serializable {
    public Difficulty(){
        this.groupIcon = R.drawable.ic_difficulty_1;
        this.isMultiChoice = true;
        this.setName("Difficultly Level");
        this.addOption( new SortingCategory("Beginner", "Difficulty", "1"));
        this.addOption( new SortingCategory("Intermediate","Difficulty","2"));
        this.addOption( new SortingCategory("Difficult","Difficulty","3"));
        this.addOption( new SortingCategory("Advanced","Difficulty","4"));
    }
}
