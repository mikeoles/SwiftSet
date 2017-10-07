package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.R;
import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;

public class Plyometrics extends SortingGroup implements Serializable {
    public Plyometrics(){
        this.groupIcon = R.drawable.ic_plyos;
        this.addCantFollow(Joints.class);
        this.addCantFollow(MuscleGroup.class);
        this.addCantFollow(Tempo.class);
        this.addCantFollow(PushPullLegs.class);
        this.addCantFollow(MuscleGroup.class);
        this.addCantFollow(Sport.class);
        this.addCantFollow(Equipment.class);
        this.setName("Plyometrics");
        this.addOption(new SortingCategory("Plyos","Primary","Plyos"));
    }
}