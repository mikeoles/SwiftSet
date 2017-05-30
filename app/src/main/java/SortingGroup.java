import java.util.ArrayList;

/**
 * Created by Oles on 5/29/2017.
 * A sorting group is a method in which exercises can be narrowed down.
 * Ex: Push/Pull/Legs, Muscle Group, Equipment used
 * @
 */
public class SortingGroup {
    //All of the sorting groups that cant be used after this sorting group is used
    //Ex: You can't sort by Upper/Lower after Muscle Group is called
    private ArrayList<SortingGroup> cantFollow;
    //All of the categories that can be chosen from this sorting group
    //Ex: Muscle Groups categories: Chest, Triceps, Quads, Lats...
    private ArrayList<SortingOptions> categories;
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SortingOptions> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<SortingOptions> categories) {
        this.categories = categories;
    }

    public ArrayList<SortingGroup> getCantFollow() {
        return cantFollow;
    }

    public void setCantFollow(ArrayList<SortingGroup> cantFollow) {
        this.cantFollow = cantFollow;
    }

}
