package com.michaeloles.swiftset.SortingGroups;

import com.michaeloles.swiftset.SortingCategory;
import com.michaeloles.swiftset.SortingGroup;

import java.io.Serializable;

public class Tempo extends SortingGroup implements Serializable {
    public Tempo(){
        this.setName("Tempo");
        this.addOption(new SortingCategory("Normal", "Tempo", "Normal"));
        this.addOption( new SortingCategory("Explosive","Tempo","Explosive"));
        this.addOption( new SortingCategory("Isometric","Tempo","Isometric"));
        this.addOption( new SortingCategory("Eccentric","Tempo","Eccentric"));
        this.addOption( new SortingCategory("Dead Stop","Tempo","Dead Stop"));
        this.addOption( new SortingCategory("Pause Reps","Tempo","Pause"));
        this.addOption( new SortingCategory("Partial Reps","Tempo","Partial"));
    }
}