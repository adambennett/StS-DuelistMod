package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuPanelButton;
import duelistmod.ui.DuelistMainMenuPanelButton;

@SpirePatch(clz = MainMenuPanelButton.class, method = "setLabel")
public class MenuPanelButtonPatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(final MainMenuPanelButton instance) {
        if (instance instanceof DuelistMainMenuPanelButton) {
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }

}
