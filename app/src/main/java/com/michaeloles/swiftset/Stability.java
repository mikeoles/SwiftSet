package com.michaeloles.swiftset;

import java.io.Serializable;

public class Stability extends SortingGroup implements Serializable {
    public Stability(){
        this.setName("Stability/Balance Exercise");
        this.addOption(new SortingCategory("Stability","Stability","1"));
    }
}