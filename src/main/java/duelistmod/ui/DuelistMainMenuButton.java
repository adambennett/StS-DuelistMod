package duelistmod.ui;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;
import duelistmod.DuelistMod;
import duelistmod.enums.ConfigOpenSource;
import duelistmod.helpers.Util;
import duelistmod.enums.MainMenuPatchEnums;

import static duelistmod.enums.MainMenuPanelEnums.MAIN_HUB;

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
        } else if (this.result == MainMenuPatchEnums.DUELIST_MENU) {
            DuelistMod.mainMenuPanelScreen.open(MAIN_HUB);
        }
    }
}
