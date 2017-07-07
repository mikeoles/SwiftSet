package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.io.Serializable;


public class MuscleGroup extends SortingGroup implements Serializable {
    public MuscleGroup(){
        this.setName("Muscle Group");
        this.addCantFollow(PushPullLegs.class);
        SortingCategory chest = new SortingCategory("Chest","Primary","Chest");
        chest.addNewOptions(new ChestMovementPatterns());
        this.addOption(chest);
        SortingCategory triceps = new SortingCategory("Triceps","Primary","Triceps");
        triceps.addNewOptions(new TricepMovementPatterns());
        this.addOption(triceps);
        SortingCategory lats = new SortingCategory("Lats","Primary","Lats");
        this.addOption(lats);
        SortingCategory quads = new SortingCategory("Quads","Primary","Quads");
        this.addOption(quads);
        SortingCategory hamstrings = new SortingCategory("Hamstrings","Primary","Hamstrings");
        this.addOption(hamstrings);
    }
}