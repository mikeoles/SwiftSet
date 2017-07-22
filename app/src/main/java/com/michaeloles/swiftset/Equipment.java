package com.michaeloles.swiftset;

import java.io.Serializable;

//Todo allow for selection of multiple

public class Equipment extends SortingGroup implements Serializable {
    public Equipment(){
        this.setName("Equipment");
        SortingCategory barbell = new SortingCategory("Barbell","Equipment","Barbell");
        this.addOption(barbell);
        SortingCategory trapbar = new SortingCategory("Trap Bar","Equipment","Trap Bar");
        this.addOption(trapbar);
        SortingCategory dumbbell = new SortingCategory("Dumbbell","Equipment","Dumbbell");
        this.addOption(dumbbell);
        SortingCategory machine = new SortingCategory("Machine","Equipment","Machine");
        this.addOption(machine);
        SortingCategory smith = new SortingCategory("Smith Machine","Equipment","Smith");
        this.addOption(smith);
        SortingCategory cable = new SortingCategory("Cable","Equipment","Cable");
        this.addOption(cable);
        SortingCategory landmine = new SortingCategory("Landmine","Equipment","Landmine");
        this.addOption(landmine);
        SortingCategory kettlebell = new SortingCategory("Kettlebell","Equipment","Kettlebell");
        this.addOption(kettlebell);
        SortingCategory medball = new SortingCategory("Medball","Equipment","Medball");
        this.addOption(medball);
        SortingCategory chains = new SortingCategory("Chains","Equipment","Chains");
        this.addOption(chains);
        SortingCategory bands = new SortingCategory("Bands","Equipment","Banded");
        this.addOption(bands);
        SortingCategory bosu = new SortingCategory("Bosu Ball","Equipment","Bosu");
        this.addOption(bosu);
        SortingCategory sled = new SortingCategory("Sled/Prowler","Equipment","Sled");
        this.addOption(sled);
        SortingCategory plate = new SortingCategory("Weight Plate","Equipment","Plate");
        this.addOption(plate);
        SortingCategory safetyBar = new SortingCategory("Safety Bar","Equipment","Safety Bar");
        this.addOption(safetyBar);
        SortingCategory weightedBelt = new SortingCategory("Weighted Belt","Equipment","Weighted Belt");
        this.addOption(weightedBelt);
        SortingCategory gluteham = new SortingCategory("Glute Ham Machine","Equipment","Glute Ham");
        this.addOption(gluteham);
        SortingCategory hangingBand = new SortingCategory("Haning Band","Equipment","Hanging Band");
        this.addOption(hangingBand);
        SortingCategory other = new SortingCategory("Other","Equipment","Other");
        this.addOption(other);
    }
}