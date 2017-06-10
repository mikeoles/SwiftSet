package edu.pitt.cs.cs1635.mbo10.swiftset;

/**
 * Created by Oles on 5/30/2017.
 */
public class MuscleGroup extends SortingGroup {
    public MuscleGroup(){
        this.setName("Muscle Group");
        SortingCategory chest = new SortingCategory("Chest","Primary","Chest");
        this.addOption(chest);
        SortingCategory triceps = new SortingCategory("Triceps","Primary","Triceps");
        this.addOption(triceps);
        SortingCategory lats = new SortingCategory("Lats","Primary","Lats");
        this.addOption(lats);
        SortingCategory quads = new SortingCategory("Quads","Primary","Quads");
        this.addOption(quads);
        SortingCategory hamstrings = new SortingCategory("Hamstrings","Primary","Hamstrings");
        this.addOption(hamstrings);
    }
}