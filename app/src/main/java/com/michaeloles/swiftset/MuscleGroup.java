package com.michaeloles.swiftset;

import java.io.Serializable;


public class MuscleGroup extends SortingGroup implements Serializable {
    public MuscleGroup(){
        this.setName("Muscle Group");
        this.addCantFollow(PushPullLegs.class);
        SortingCategory chest = new SortingCategory("Chest","Primary","Chest");
        chest.addNewOptions(new ChestMovementPatterns());
        this.addOption(chest);
        SortingCategory shoulder = new SortingCategory("Shoulders","Primary","Shoulders");
        shoulder.addNewOptions(new ShoulderMovementPatterns());
        this.addOption(shoulder);
        SortingCategory traps = new SortingCategory("Traps","Primary","Traps");
        this.addOption(traps);
        SortingCategory lats = new SortingCategory("Lats","Primary","Lats");
        lats.addNewOptions(new LatMovementPatterns());
        lats.addNewOptions(new Grip());
        this.addOption(lats);
        SortingCategory reardelts = new SortingCategory("Rear Delts","Primary","Rear Delts");
        reardelts.addNewOptions(new Grip());
        this.addOption(reardelts);
        SortingCategory triceps = new SortingCategory("Triceps","Primary","Triceps");
        triceps.addNewOptions(new Grip());
        triceps.addNewOptions(new TricepMovementPatterns());
        this.addOption(triceps);
        SortingCategory biceps = new SortingCategory("Biceps","Primary","Biceps");
        biceps.addNewOptions(new Grip());
        this.addOption(biceps);
        SortingCategory forearms = new SortingCategory("Forearms","Primary","Forearms");
        forearms.addNewOptions(new ForearmMovementPatterns());
        this.addOption(forearms);
        SortingCategory neck = new SortingCategory("Neck","Primary","Neck");
        this.addOption(neck);
        SortingCategory core = new SortingCategory("Core","Primary","Core");
        core.addNewOptions(new CoreMovementPatterns());
        this.addOption(core);
        SortingCategory lowerback = new SortingCategory("Lower Back","Primary","Lower Back");
        this.addOption(lowerback);
        SortingCategory quads = new SortingCategory("Quads","Primary","Quads");
        this.addOption(quads);
        SortingCategory hamstrings = new SortingCategory("Hamstrings","Primary","Hamstrings");
        hamstrings.addNewOptions(new HamstringMovementPatterns());
        this.addOption(hamstrings);
        SortingCategory calves = new SortingCategory("Calves","Primary","Calf");
        this.addOption(calves);
        SortingCategory glute = new SortingCategory("Glutes","Primary","Glutes");
        this.addOption(glute);
        SortingCategory hips = new SortingCategory("Hips","Primary","Hips");
        hips.addNewOptions(new HipMovementPatterns());
        this.addOption(hips);
    }
}