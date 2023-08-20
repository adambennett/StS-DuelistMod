package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.SensoryStone;
import duelistmod.DuelistMod;

public class SensoryStonePatch {

    @SpirePatch(clz = SensoryStone.class, method = "reward")
    public static class GetReward {
        public static SpireReturn<Void> Prefix(SensoryStone __instance, int num) {
            if(AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST) && (DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveCardRewards() || DuelistMod.persistentDuelistData.CardPoolSettings.getAlwaysBoosters())) {
                DuelistMod.isSensoryStone = true;
                return SpireReturn.Continue();
            }
            return SpireReturn.Continue();
        }
    }

}
