package duelistmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.*;
import duelistmod.DuelistMod;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.Util;
import duelistmod.ui.*;

public class CardRewardScreenPatches {

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

    @SpirePatch(clz = CardRewardScreen.class, method = "cardSelectUpdate")
    public static class ManualDraftSizePatch {
        private static int actualDraftCount = 0;

        public static SpireReturn<Void> Prefix(CardRewardScreen __instance) {
            if (!(AbstractDungeon.player instanceof TheDuelist)) return SpireReturn.Continue();
            boolean draft = ReflectionHacks.getPrivate(__instance, CardRewardScreen.class, "draft");
            int draftCount = ReflectionHacks.getPrivate(__instance, CardRewardScreen.class, "draftCount");
            int newDraftLimit = CustomModeScreenPatches.draftLimit;
            if (draft && (((actualDraftCount + draftCount) >= (newDraftLimit + 1)) || draftCount >= 15)) {
                actualDraftCount += draftCount;
                ReflectionHacks.setPrivate(__instance, CardRewardScreen.class, "draftCount", 0);
                if (actualDraftCount >= (newDraftLimit + 1)) {
                    actualDraftCount = 0;
                    AbstractDungeon.closeCurrentScreen();
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz= CardRewardScreen.class, method="update")
    public static class CardRewardScreenPatch_Update {
        @SpirePostfixPatch
        public static void Postfix(CardRewardScreen __instance)
        {
            TierScoreRewardScreen.update();
        }
    }

    @SpirePatch(clz = CardRewardScreen.class, method = "acquireCard")
    public static class AcquireCardFromRewardPatch {

        public static void Postfix() {
            if (DuelistMod.currentReward != null) {
                Util.log("Opened booster pack " + DuelistMod.currentReward.getUniquePackName());
                DuelistMod.boostersOpenedThisRun.compute(DuelistMod.currentReward.getUniquePackName(), (k, v) -> v == null ? 1 : v + 1);
                DuelistMod.boostersOpenedThisAct.compute(DuelistMod.currentReward.getUniquePackName(), (k, v) -> v == null ? 1 : v + 1);
            } else {
                Util.log("Opened card reward");
            }
            DuelistMod.currentReward = null;
        }
    }

}
