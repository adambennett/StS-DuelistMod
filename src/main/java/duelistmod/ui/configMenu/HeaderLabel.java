package duelistmod.ui.configMenu;

import basemod.ModLabel;
import duelistmod.DuelistMod;

public class HeaderLabel extends ModLabel {

    public HeaderLabel(String text) {
        super(text, DuelistMod.xLabPos + DuelistMod.xSecondCol - 35, DuelistMod.yPos, DuelistMod.settingsPanel, x->{});
        DuelistMod.linebreak();
        DuelistMod.linebreak();
    }
}
