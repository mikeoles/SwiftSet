package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.R;
import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;

public class Equipment extends SortingGroup implements Serializable {
    public Equipment(){
        this.groupIcon = R.drawable.ic_equipment;
        this.isMultiChoice = true;

        this.setName("Equipment");
        SortingCategory barbell = new SortingCategory("Barbell","Equipment","Barbell");
        this.addOption(barbell);
        SortingCategory dumbbell = new SortingCategory("Dumbbell","Equipment","Dumbbell");
        this.addOption(dumbbell);
        SortingCategory bodyweight = new SortingCategory("Bodyweight","Equipment","Bodyweight");
        this.addOption(bodyweight);
        SortingCategory machine = new SortingCategory("Machine","Equipment","Machine");
        this.addOption(machine);
        SortingCategory cable = new SortingCategory("Cable","Equipment","Cable");
        this.addOption(cable);
        SortingCategory trapbar = new SortingCategory("Trap Bar","Equipment","Trap Bar");
        this.addOption(trapbar);
        SortingCategory smith = new SortingCategory("Smith Machine","Equipment","Smith");
        this.addOption(smith);
        SortingCategory landmine = new SortingCategory("Landmine","Equipment","Landmine");
        this.addOption(landmine);
        SortingCategory weightedBelt = new SortingCategory("Weighted Belt","Equipment","Weighted Belt");
        this.addOption(weightedBelt);
        SortingCategory kettlebell = new SortingCategory("Kettlebell","Equipment","Kettlebell");
        this.addOption(kettlebell);
        SortingCategory medball = new SortingCategory("Medball","Equipment","Medball");
        this.addOption(medball);
        SortingCategory stability = new SortingCategory("Stability Ball","Equipment","Stability Ball");
        this.addOption(stability);
        SortingCategory bosu = new SortingCategory("Bosu Ball","Equipment","Bosu");
        this.addOption(bosu);
        SortingCategory chains = new SortingCategory("Chains","Equipment","Chains");
        this.addOption(chains);
        SortingCategory bands = new SortingCategory("Bands","Equipment","Banded");
        this.addOption(bands);
        SortingCategory sled = new SortingCategory("Sled/Prowler","Equipment","Sled");
        this.addOption(sled);
        SortingCategory plate = new SortingCategory("Weight Plate","Equipment","Weight Plate");
        this.addOption(plate);
        SortingCategory safetyBar = new SortingCategory("Specialized Bars","Equipment","Specialized Bars");
        this.addOption(safetyBar);
        SortingCategory gluteham = new SortingCategory("Glute Ham Machine","Equipment","Glute Ham");
        this.addOption(gluteham);
        SortingCategory hangingBand = new SortingCategory("Hanging Band","Equipment","Hanging Band");
        this.addOption(hangingBand);
        SortingCategory directBand = new SortingCategory("Direct Band","Equipment","Direct Band");
        this.addOption(directBand);
        SortingCategory foamRoller = new SortingCategory("Foam Roller","Equipment","Foam Roller");
        this.addOption(foamRoller);
        SortingCategory battlerope = new SortingCategory("Battle Rope","Equipment","Battle Rope");
        this.addOption(battlerope);
        SortingCategory trx = new SortingCategory("TRX","Equipment","TRX");
        this.addOption(trx);
        SortingCategory other = new SortingCategory("Other","Equipment","Other");
        this.addOption(other);
    }
}