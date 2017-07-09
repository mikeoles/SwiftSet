package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.io.Serializable;

public class Unilateral extends SortingGroup implements Serializable {
    public Unilateral(){
        this.setName("Unilateral/Bilateral");
        this.addOption(new SortingCategory("Unilateral","Unilateral","1"));
        this.addOption( new SortingCategory("Bilateral","Unilateral","0"));
    }
}