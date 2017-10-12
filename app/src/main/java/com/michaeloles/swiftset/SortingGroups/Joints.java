package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.R;
import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;

public class Joints extends SortingGroup implements Serializable {
    public Joints(){
        this.groupIcon = R.drawable.ic_joint;
        this.setName("Compound/Isolation");
        this.addCantFollow(Plyometrics.class);
        this.addOption(new SortingCategory("Compound","Joint","Compound"));
        this.addOption( new SortingCategory("Isolation","Joint","Isolation"));
    }
}