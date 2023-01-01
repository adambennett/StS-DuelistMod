package duelistmod.ui;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;
import duelistmod.enums.ConfigOpenSource;
import duelistmod.helpers.Util;
import duelistmod.patches.MainMenuPatchEnums;

public class DuelistMainMenuButton extends MenuButton {

    private String title;

    public DuelistMainMenuButton(ClickResult r, int index, String label) {
        super(r, index);
        this.setTitle(label);
    }

    public void setTitle(String label) {
        ReflectionHacks.setPrivateInherited(this, this.getClass(), "label", label);
        this.title = label;
    }

    public String title() { return this.title; }

    @Override
    public void buttonEffect() {
        if (this.result == MainMenuPatchEnums.DUELIST_CONFIG) {
            Util.openModSettings(ConfigOpenSource.MAIN_MENU);
        }
    }
}
