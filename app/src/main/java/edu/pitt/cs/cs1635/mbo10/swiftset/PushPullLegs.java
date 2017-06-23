package edu.pitt.cs.cs1635.mbo10.swiftset;

import java.io.Serializable;

/**
 * Created by Oles on 5/30/2017.
 */
public class PushPullLegs extends SortingGroup implements Serializable{
    public PushPullLegs(){
        this.setName("Push,Pull,Legs");
        this.addCantFollow(new MuscleGroup());
        
        SortingCategory push = new SortingCategory("Push","PPL","Push");
        this.addOption(push);
        SortingCategory pull = new SortingCategory("Pull","PPL","Pull");
        this.addOption(pull);
        SortingCategory legs = new SortingCategory("Legs","PPL","Legs");
        this.addOption(legs);        
    }
}
