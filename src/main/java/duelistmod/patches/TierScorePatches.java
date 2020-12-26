package duelistmod.patches;

import com.badlogic.gdx.graphics.g2d.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.screens.*;
import duelistmod.ui.*;

public class TierScorePatches {

    @SpirePatch(clz= CardRewardScreen.class, method="onClose")
    public static class CardRewardScreenPatch_OnClose
    {
        @SpirePostfixPatch
        public static void Postfix(CardRewardScreen __instance)
        {
            TierScoreRewardScreen.onClose();
        }
    }

    @SpirePatch(clz= CardRewardScreen.class, method="render")
    public static class CardRewardScreenPatch_Render
    {
        @SpirePrefixPatch
        public static void Prefix(CardRewardScreen __instance, SpriteBatch sb) { TierScoreRewardScreen.preRender(__instance, sb); }
    }

    @SpirePatch(clz= CardRewardScreen.class, method="update")
    public static class CardRewardScreenPatch_Update
    {
        @SpirePostfixPatch
        public static void Postfix(CardRewardScreen __instance)
        {
            TierScoreRewardScreen.update();
        }
    }

}
