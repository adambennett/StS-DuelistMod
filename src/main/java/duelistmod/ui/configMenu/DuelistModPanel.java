package duelistmod.ui.configMenu;

import basemod.ModPanel;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;
import duelistmod.DuelistMod;
import duelistmod.helpers.Util;

public class DuelistModPanel extends ModPanel implements DropdownMenuListener {

    @Override
    public void changedSelectionTo(DropdownMenu dropdownMenu, int i, String s) {
        if (dropdownMenu instanceof DuelistDropdown) {
            DuelistDropdown menu = ((DuelistDropdown)dropdownMenu);
            if (menu.hasOnChange()) {
                menu.change(s, i);
            } else {
                Util.log("Unimplemented dropdown menu changed to option " + i + " (" + s + ")");
            }
        }
    }

    private void onClose() {
        Util.log("Closed DuelistModPanel");
        DuelistMod.paginator.resetToPageOne();
    }

    @Override
    public void update() {
        super.update();
        if (!this.isUp) {
            this.onClose();
        }
    }
}
