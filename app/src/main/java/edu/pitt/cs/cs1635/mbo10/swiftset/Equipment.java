package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.io.Serializable;


public class Equipment extends SortingGroup implements Serializable {
    public Equipment(){
        this.setName("Equipment");
        SortingCategory barbell = new SortingCategory("Barbell","Equipment","Barbell");
        this.addOption(barbell);
        SortingCategory trapbar = new SortingCategory("Trap Bar","Equipment","Trap Bar");
        this.addOption(trapbar);
        SortingCategory dumbbell = new SortingCategory("Dumbell","Equipment","Dumbbell");
        this.addOption(dumbbell);
        SortingCategory machine = new SortingCategory("Machine","Equipment","Machine");
        this.addOption(machine);
        SortingCategory cable = new SortingCategory("Cable","Equipment","Cable");
        this.addOption(cable);
        SortingCategory kettlebell = new SortingCategory("Kettlebell","Equipment","Kettlebell");
        this.addOption(kettlebell);
        SortingCategory medball = new SortingCategory("Medball","Equipment","Medball");
        this.addOption(medball);
        SortingCategory chains = new SortingCategory("Chains","Equipment","Chains");
        this.addOption(chains);
        SortingCategory bands = new SortingCategory("Bands","Equipment","Bands");
        this.addOption(bands);
        SortingCategory bosu = new SortingCategory("Bosu Ball","Equipment","Bosu Ball");
        this.addOption(bosu);
    }
}