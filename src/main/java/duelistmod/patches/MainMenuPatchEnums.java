package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.mainMenu.*;

public class MainMenuPatchEnums {

    @SpireEnum
    public static MenuButton.ClickResult DUELIST_MENU;

    @SpireEnum
    public static MainMenuScreen.CurScreen DUELIST_SCREEN;

    @SpireEnum
    public static MainMenuScreen.CurScreen MAIN_MENU_CONFIG_SCREEN;

    @SpireEnum
    public static AbstractDungeon.CurrentScreen MID_RUN_CONFIG_SCREEN;

    @SpireEnum
    public static MenuButton.ClickResult DUELIST_CONFIG;
}
