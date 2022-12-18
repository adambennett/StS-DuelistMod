package duelistmod.ui.configMenu;

import duelistmod.enums.DropdownMenuType;

import java.util.ArrayList;

public class DuelistDropdownDynamicWidth extends DuelistDropdown {

    private final float widthModifier;

    public DuelistDropdownDynamicWidth(ArrayList<String> options, int xPos, int yPos, float widthModifier, DropdownMenuListener listener) {
        super(options, xPos, yPos, listener);
        this.widthModifier = widthModifier;
    }

    public DuelistDropdownDynamicWidth(ArrayList<String> options, float xPos, float yPos, float widthModifier, DropdownMenuListener listener) {
        this(options, (int)xPos, (int)yPos, widthModifier, listener);
    }

    @Override
    public float approximateOverallWidth() {
        float sup = super.approximateOverallWidth();
        return sup + this.widthModifier;
    }

}
