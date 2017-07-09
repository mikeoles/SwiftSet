package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.io.Serializable;


public class Equipment extends SortingGroup implements Serializable {
    public Equipment(){
        this.setName("Equipment");
        SortingCategory barbell = new SortingCategory("Barbell","Primary","Barbell");
        this.addOption(barbell);
        SortingCategory trapbar = new SortingCategory("Trap Bar","Primary","Trap Bar");
        this.addOption(trapbar);
        SortingCategory dumbbell = new SortingCategory("Dumbell","Primary","Dumbbell");
        this.addOption(dumbbell);
        SortingCategory machine = new SortingCategory("Machine","Primary","Machine");
        this.addOption(machine);
        SortingCategory cable = new SortingCategory("Cable","Primary","Cable");
        this.addOption(cable);
        SortingCategory kettlebell = new SortingCategory("Kettlebell","Primary","Kettlebell");
        this.addOption(kettlebell);
        SortingCategory medball = new SortingCategory("Medball","Primary","Medball");
        this.addOption(medball);
        SortingCategory chains = new SortingCategory("Chains","Primary","Chains");
        this.addOption(chains);
        SortingCategory bands = new SortingCategory("Bands","Primary","Bands");
        this.addOption(bands);
    }
}