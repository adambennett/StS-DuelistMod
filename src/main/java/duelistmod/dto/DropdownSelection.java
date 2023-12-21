package duelistmod.dto;

import com.megacrit.cardcrawl.helpers.Hitbox;

public class DropdownSelection {

    private final String text;
    private final int index;
    private final Hitbox hb;

    public DropdownSelection(String text, int index, Hitbox hb) {
        this.text = text;
        this.index = index;
        this.hb = hb;
    }

    public String text() { return this.text; }

    public int index() { return this.index; }

    public Hitbox hb() { return this.hb; }

    @Override
    public String toString() {
        return "{ text='" + text + '\'' +
                ", index=" + index +
                ", hb=" + hb +
                " }";
    }
}
