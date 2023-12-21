package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import duelistmod.helpers.BonusDeckUnlockHelper;

@SpirePatch(clz = CorruptHeart.class, method="die")
public class HeartCheckPatch {
    public static void Prefix() {
    	if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST)) {
			BonusDeckUnlockHelper.beatHeart();
    	}
    }
}
