package duelistmod.patches.crossmod;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import duelistmod.DuelistMod;
import extendedui.ui.screens.CardPoolScreen;
import extendedui.ui.screens.EUIPoolScreen;

public class CompendiumFiltersPatches {

    @SuppressWarnings("unused")
    @SpirePatch(clz = EUIPoolScreen.class, method = "open", optional = true, requiredModId = "extendedui")
    public static class LobbyConstructorPatch {
        public static SpireReturn<Void> Prefix(EUIPoolScreen __instance) {
            if (DuelistMod.openedModSettings && __instance instanceof CardPoolScreen) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

}
