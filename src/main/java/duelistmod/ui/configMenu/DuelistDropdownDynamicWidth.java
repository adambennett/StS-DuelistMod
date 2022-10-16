package duelistmod.ui.configMenu;

import duelistmod.enums.DropdownMenuType;

import java.util.ArrayList;

public class DuelistDropdownDynamicWidth extends DuelistDropdown {

    private final float widthModifier;

    public DuelistDropdownDynamicWidth(ArrayList<String> options, int xPos, int yPos, DropdownMenuType type, float widthModifier) {
        super(options, xPos, yPos, type);
        this.widthModifier = widthModifier;
    }

    public DuelistDropdownDynamicWidth(ArrayList<String> options, float xPos, float yPos, DropdownMenuType type, float widthModifier) {
        this(options, (int)xPos, (int)yPos, type, widthModifier);
    }

    @Override
    public float approximateOverallWidth() {
        float sup = super.approximateOverallWidth();
        return sup + this.widthModifier;
    }

}
