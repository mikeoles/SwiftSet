package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.io.Serializable;

public class Tempo extends SortingGroup implements Serializable {
    public Tempo(){
        this.setName("Tempo");
        this.addOption(new SortingCategory("Normal", "Tempo", "Normal"));
        this.addOption( new SortingCategory("Explosive","Tempo","Explosive"));
        this.addOption( new SortingCategory("Isometric","Tempo","Isometric"));
        this.addOption( new SortingCategory("Eccentric","Tempo","Eccentric"));
    }
}