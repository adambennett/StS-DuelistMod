package duelistmod.dto;

import java.util.ArrayList;

public class CardPoolRelicFilter {

    private final ArrayList<String> toAdd;
    private final ArrayList<String> toRemove;

    public CardPoolRelicFilter(ArrayList<String> toAdd, ArrayList<String> toRemove) {
        this.toAdd = toAdd;
        this.toRemove = toRemove;
    }

    public ArrayList<String> getToAdd() {
        return toAdd;
    }

    public ArrayList<String> getToRemove() {
        return toRemove;
    }
}
