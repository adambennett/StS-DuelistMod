package duelistmod.patches.crossmod;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import duelistmod.DuelistMod;
import extendedui.ui.cardFilter.CardPoolScreen;

public class CompendiumFiltersPatches {

    @SuppressWarnings("unused")
    @SpirePatch(clz = CardPoolScreen.class, method = "open", optional = true, requiredModId = "extendedui")
    public static class LobbyConstructorPatch {
        public static SpireReturn<Void> Prefix() {
            if (DuelistMod.openedModSettings) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

}
