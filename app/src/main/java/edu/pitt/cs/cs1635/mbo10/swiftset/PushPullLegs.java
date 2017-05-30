package edu.pitt.cs.cs1635.mbo10.swiftset;

/**
 * Created by Oles on 5/30/2017.
 */
public class PushPullLegs extends SortingGroup {
    public PushPullLegs(){
        this.setName("Push,Pull,Legs");
        this.addOption("Push");
        this.addOption("Pull");
        this.addOption("Legs");
        this.addCantFollow(new MuscleGroup());
    }
}
